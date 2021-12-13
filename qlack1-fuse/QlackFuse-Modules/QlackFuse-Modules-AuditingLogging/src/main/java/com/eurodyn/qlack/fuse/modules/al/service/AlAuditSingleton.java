package com.eurodyn.qlack.fuse.modules.al.service;

import com.eurodyn.qlack.fuse.modules.al.model.AlAuditLevel;

import javax.ejb.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A Audit Level loader mechanism . The list of Auditlevels are maintained in allAuditLevels.
 *
 * @author European Dynamics SA.
 */
@Singleton(name = "AlAuditSingletonImpl")
public class AlAuditSingleton {


    private AuditLevelManager auditLevelManager;

    private static final Logger logger = Logger.getLogger(AlAuditSingleton.class.getName());

    private static AlAuditSingleton _instance = new AlAuditSingleton();

    private static List<AlAuditLevel> allAuditLevels;

    private static Map<String, AlAuditLevel> auditLevelMap;
    private Context context;


    private AlAuditSingleton() {
        try {
            this.context = new InitialContext();
        } catch (NamingException e) {
            logger.severe("Error Instantiating:" + e.getMessage());
        }
        logger.info("Retrieving all audit levels");
    }


    /**
     *
     */
    public static AlAuditSingleton getInstance() {
        return _instance;
    }


    public void reloadAlAuditLevels() {

        try {
            if (context == null) {
                this.context = new InitialContext();
            }

            auditLevelManager = (AuditLevelManager) this.context.lookup("java:app/QlackFuse-Modules-AuditingLogging/AuditLevelManagerBean");

            logger.info("Retrieving all audit levels");
            List<AlAuditLevel> auditLevels = auditLevelManager.getAllAudLevelsFromDB();

            if (auditLevelMap == null) {
                auditLevelMap = new HashMap<>();
            } else {
                auditLevelMap.clear();
            }

            auditLevelMap
                    .putAll(auditLevels.stream().collect(Collectors.toMap(AlAuditLevel::getName, Function.identity())));

            if (allAuditLevels == null) {
                allAuditLevels = new ArrayList<>();
            } else {
                allAuditLevels.clear();
            }

            allAuditLevels.addAll(auditLevels);

        } catch (NamingException e) {
            logger.severe("Error Instantiating:" + e.getMessage());
        }


    }


    public AlAuditLevel getAuditLevelByName(String propertyName) {
        if (auditLevelMap == null) {
            getInstance().reloadAlAuditLevels();
        }
        return auditLevelMap.get(propertyName);

    }


    public List<AlAuditLevel> getAllAuditLevels() {
        if (allAuditLevels == null) {
            getInstance().reloadAlAuditLevels();
        }
        return allAuditLevels;
    }

    public static void setAllAuditLevels(List<AlAuditLevel> allAuditLevels) {
        AlAuditSingleton.allAuditLevels = allAuditLevels;
    }

    public static void setAuditLevelMap(Map<String, AlAuditLevel> auditLevelMap) {
        AlAuditSingleton.auditLevelMap = auditLevelMap;
    }

    public static Map<String, AlAuditLevel> getAuditLevelMap() {
        return auditLevelMap;
    }

}
