package ua.kiev.prog;

import java.util.List;


public interface AdvDAO
{
	List<Advertisement> list();
    List<Advertisement> list(String pattern);
    // Список удаленных объявлений
    List<Advertisement> trashList();

	void add(Advertisement adv);
    void add(List<Advertisement> advs);

    void delete(long id);
    void delete(long[] ids);

    void remove(long id);
    void remove(long[] ids);

    void restore(long id);
    void restore(long[] ids);

    byte[] getPhoto(long id);
}
