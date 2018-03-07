package com.elab.consume.checker.suivi.consomation;

import com.elab.consume.utils.tools.CommonCriterias;

import java.util.List;

/**
 * Created by NAWAR on 28/02/2018.
 */

public interface ConsomationManageableService {

    public void create(Consomation entity);
    public void create(List<Consomation> entities);
    public void update(Consomation entity);
    public void update(List<Consomation> entities);
    public Consomation read(int id);
    public void delete(int id);
    public List<Consomation> readAll(int user_id);
    public List<Consomation> readByCritireas(CommonCriterias commonCriterias);
}
