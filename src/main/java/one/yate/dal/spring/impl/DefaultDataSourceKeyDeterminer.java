package one.yate.dal.spring.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import one.yate.dal.spring.DataSourceKeyDeterminer;
import one.yate.dal.spring.DataSourceKeyRule;

public class DefaultDataSourceKeyDeterminer implements DataSourceKeyDeterminer {

    private static Logger log = LoggerFactory
            .getLogger(DefaultDataSourceKeyDeterminer.class);

    protected String defaultDataSourceKey;
    protected List<DataSourceKeyRule> rules = new ArrayList<DataSourceKeyRule>();

    public String determine(String field, Long value, boolean readonly) {
        for (DataSourceKeyRule dataSourceKeyRule : rules) {
            if (dataSourceKeyRule.applyRule(field, value, readonly)) {
                log.debug("============determine to use datasource: field-"
                        + field + "/value-" + value + "/datasource-"
                        + dataSourceKeyRule.getDataSourceKey());
                return dataSourceKeyRule.getDataSourceKey();
            }
        }
        return getDefaultDataSourceKey();
    }

    public String determine(String field, Integer value, boolean readonly) {
        return determine(field, new Long(value), readonly);
    }

    public String getDefaultDataSourceKey() {
        return defaultDataSourceKey;
    }

    public void setDefaultDataSourceKey(String defaultDataSourceKey) {
        this.defaultDataSourceKey = defaultDataSourceKey;
    }

    public List<DataSourceKeyRule> getRules() {
        return rules;
    }

    public void setRules(List<DataSourceKeyRule> rules) {
        this.rules = rules;
    }
}
