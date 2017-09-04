package com.cnpc.framework.query.pojo;

import com.cnpc.framework.query.entity.Query;
import com.cnpc.framework.query.entity.QueryContext;
import com.cnpc.framework.utils.ConfigurationUtil;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QueryDefinition {

    public static final String DEFAULT_CONFIG_LOCATION = "query/*.xml";

    static final Logger log = Logger.getLogger(QueryDefinition.class);

    private static QueryDefinition instance = new QueryDefinition();

    private final Map querys = new HashMap();

    private final Map cachedFiles = new HashMap();

    private int cachedFilesCount;

    private QueryDefinition() {


    }

    public void initQuery(){
        cachedFilesCount = 0;
        Resource resources[] = ConfigurationUtil.getAllResources(DEFAULT_CONFIG_LOCATION);
        if (resources != null) {
            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];
                log.info("Loading query from {" + resource.toString() + "}");
                try {
                    QueryContext queryContext = (QueryContext) ConfigurationUtil.parseXMLObject(
                            com.cnpc.framework.query.entity.QueryContext.class, resource);
                    List list = queryContext.getQueries();
                    Iterator it = list.iterator();
                    do {
                        if (!it.hasNext())
                            break;
                        Query query = (Query) it.next();
                        Query previous = (Query) querys.put(query.getId(), query);
                        if (previous != null)
                            log.error("Duplicated Query register! id[{" + query.getId() + "}], in file {" + resource.toString() + "}");
                    } while (true);
                    if (resource.getURL().getProtocol().equals("file"))
                        cachedFiles.put(resource, Long.valueOf(resource.getFile().lastModified()));
                } catch (IOException e) {
                    log.error("Could not load query from {" + resource.toString() + "}, reason:", e);
                } catch (RuntimeException e) {
                    log.error("Fail to digester query from {" + resource + "}, reason:", e);
                }
            }

            cachedFilesCount = cachedFiles.size();
            log.debug("cached query files: {" + cachedFilesCount + "}");
        }
    }

    public static Query getQueryById(String queryId) {

        // TODO 正式发布时，请注释该行代码
        instance.update();
        return (Query) instance.querys.get(queryId);
    }

    public static Map getQuerys() {

        return instance.querys;
    }

    public static QueryDefinition getInstance() {

        return instance;
    }

    public void update() {

        if (cachedFilesCount > 0) {
            for (Iterator i = cachedFiles.keySet().iterator(); i.hasNext();) {
                Resource resource = (Resource) i.next();
                synchronized (cachedFiles) {
                    try {
                        if (resource.getFile().lastModified() > ((Long) cachedFiles.get(resource)).longValue()) {
                            QueryContext queryContext = (QueryContext) ConfigurationUtil.parseXMLObject(
                                    com.cnpc.framework.query.entity.QueryContext.class, resource);
                            List list = queryContext.getQueries();
                            Query query;
                            for (Iterator it = list.iterator(); it.hasNext(); log.debug("Update Query id[" + query.getId() + "], in {"
                                    + resource.toString() + "}")) {
                                query = (Query) it.next();
                                instance.querys.put(query.getId(), query);
                            }
                            cachedFiles.put(resource, Long.valueOf(resource.getFile().lastModified()));
                        }
                    } catch (IOException e) {
                        log.error("Could not load query from {" + resource.toString() + "}, reason:", e);
                    } catch (RuntimeException e) {
                        log.error("Fail to digester query from {" + resource.toString() + "}, reason:", e);
                    }
                }
            }

        }
    }

}
