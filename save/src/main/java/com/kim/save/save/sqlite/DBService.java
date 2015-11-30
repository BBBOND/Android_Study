package com.kim.save.save.sqlite;

import java.util.List;
import java.util.Map;

/**
 * Created by KIM on 2015/8/18.
 */
public interface DBService {
    public int addPerson(Object[] params);

    public int deletePerson(Object[] params);

    public int updatePerson(Object[] params);

    public Map<String, String> getPerson(Object[] params);

    public List<Map<String, String>> getPersons();
}
