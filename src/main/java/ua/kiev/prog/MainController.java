package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/SpringMVC_war_exploded")
public class MainController
{
	@Autowired
	private AdvDAO advDAO;

	// Конвертация строк в utf-8 для коректной работы с кириллицей
	private String getUtfString(final String paramValue) throws UnsupportedEncodingException
	{
		return new String(paramValue.getBytes("iso-8859-1"), "utf-8");
	}


	@RequestMapping("/")
	public ModelAndView listAdvs()
	{
		return new ModelAndView("index", "advs", advDAO.list());
	}


	@RequestMapping(value = "/add_page", method = RequestMethod.POST)
	public String addPage(Model model)
	{
		return "add_page";
	}


	@RequestMapping(value = "/view_trash", method = RequestMethod.POST)
	public ModelAndView viewTrash()
	{
		return new ModelAndView("trash", "advs", advDAO.trashList());
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam(value="pattern") String pattern)
	{
		return new ModelAndView("index", "advs", advDAO.list(pattern));
	}


	@RequestMapping("/image/{file_id}")
	public void getFile(HttpServletRequest request,
						HttpServletResponse response,
						@PathVariable("file_id") long fileId)
	{
		try {
			byte[] content = advDAO.getPhoto(fileId);
			response.setContentType("image/png");
			response.getOutputStream().write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addAdv(@RequestParam(value="name") String name,
							   @RequestParam(value="shortDesc") String shortDesc,
							   @RequestParam(value="longDesc", required=false) String longDesc,
							   @RequestParam(value="phone") String phone,
							   @RequestParam(value="price") double price,
							   @RequestParam(value="photo") MultipartFile inputPhoto,
							   HttpServletRequest request,
							   HttpServletResponse response)
	{
		try {
			if (!name.isEmpty() && !shortDesc.isEmpty() && !phone.isEmpty() && price > 0.0) {
				name = getUtfString(name);
				shortDesc = getUtfString(shortDesc);

				if (!longDesc.isEmpty())
					longDesc = getUtfString(longDesc);

				Photo photo = null;

				if (!inputPhoto.isEmpty())
					photo =	new Photo(inputPhoto.getOriginalFilename(), inputPhoto.getBytes());

				Advertisement adv = new Advertisement(name, shortDesc, longDesc, phone, price, photo);
				advDAO.add(adv);
			}
//			return new ModelAndView("index", "advs", advDAO.list());
			// Используем redirect: чтобы в адресной строке браузера не оставались данные, переданные из заполненной формы
			return new ModelAndView("redirect:/SpringMVC_war_exploded/");
		} catch (IOException ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			return null;
			return new ModelAndView("redirect:/SpringMVC_war_exploded/");
		}
	}


	@RequestMapping(value = "/add-file", method = RequestMethod.POST)
	public ModelAndView addAdvs(@RequestParam(value="xml-file") MultipartFile inputFile,
								HttpServletRequest request,
								HttpServletResponse response)
			throws ParserConfigurationException, SAXException
	{
		if (!inputFile.isEmpty()) {
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();

				List<Advertisement> advs = new ArrayList<>();
				XmlAdv handler = new XmlAdv(advs);

				sp.parse(new InputSource(new ByteArrayInputStream(inputFile.getBytes())), handler);
				advDAO.add(advs);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new ModelAndView("redirect:/SpringMVC_war_exploded/");
	}


	// Безвозвратное удаление объявления
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam(value="id") long id)
	{
		advDAO.delete(id);
//		return new ModelAndView("index", "advs", advDAO.list());
		return new ModelAndView("redirect:/SpringMVC_war_exploded/");
	}


	// Безвозвратное удаление выделенных объявлений
	@RequestMapping(value = "/delete_selected")
	public ModelAndView deleteSelected(@RequestParam(required = false, value = "selected") long[] ids)
	{
		advDAO.delete(ids);
		return new ModelAndView("redirect:/SpringMVC_war_exploded/");
	}


	// Удаление объявления в корзину
	@RequestMapping("/remove")
	public ModelAndView remove(@RequestParam(value="id") long id)
	{
		advDAO.remove(id);
		return new ModelAndView("redirect:/SpringMVC_war_exploded/");
	}


	// Удаление выделенных объявлений в корзину
	@RequestMapping(value = "/remove_selected")
	public ModelAndView removeSelected(@RequestParam(required = false, value = "selected") long[] ids)
	{
		advDAO.remove(ids);
		return new ModelAndView("redirect:/SpringMVC_war_exploded/");
	}


	// Восстановление объявления из корзины
	@RequestMapping("/restore")
	public ModelAndView restore(@RequestParam(value="id") long id)
	{
		advDAO.restore(id);
		return new ModelAndView("redirect:/SpringMVC_war_exploded/");
	}


	// Восстановление выделенных объявлений из корзины
	@RequestMapping(value = "/restore_selected")
	public ModelAndView restoreSelected(@RequestParam(required = false,value = "selected") long[] ids)
	{
		advDAO.restore(ids);
		return new ModelAndView("redirect:/SpringMVC_war_exploded/");
	}
}