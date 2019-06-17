package cz.muni.fi.pb138.videolibrary;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;

public class NativeXMLDatabaseManager {
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/test/";
    private final Collection collection;
    private final XQueryService xQueryService;


    NativeXMLDatabaseManager() throws Exception {
        final String driver = "org.exist.xmldb.DatabaseImpl";

        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        collection = DatabaseManager.getCollection(URI, "admin", "admin");
        xQueryService = (XQueryService) collection.getService("XQueryService", "1.0");
    }

    public void createCategory(String category) throws Exception {
        CompiledExpression expression = xQueryService.compile("update insert <category name='" + category + "' /> into doc('database.xml')/videoLibrary/categories");
        xQueryService.execute(expression);
    }
}
