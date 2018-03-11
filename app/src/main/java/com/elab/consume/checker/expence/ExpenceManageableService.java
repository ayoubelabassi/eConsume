package com.elab.consume.checker.expence;

import com.elab.consume.tools.CommonCriterias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by NAWAR on 28/02/2018.
 */

public interface ExpenceManageableService {

    public void create(Expence entity);
    public void create(List<Expence> entities);
    public void update(Expence entity);
    public void update(List<Expence> entities);
    public Expence read(int id);
    public void delete(int id);
    public List<Expence> readAll(int user_id);
    public List<Expence> readByCritireas(CommonCriterias commonCriterias);
    public List<String> readExpencesNames(int user_id);
    public Calendar getMinDate();
    public List<MonthExpence> readMonthExpences(CommonCriterias commonCriterias);
}
