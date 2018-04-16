package Test;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class MongoClientTest {
    public static  void main(String[] args){
        linkDB();
    }
    public static void linkDB2(){
        ServerAddress serverAddress = new ServerAddress("localhost",27017);
        List<ServerAddress> addrs = new ArrayList<>();
        addrs.add(serverAddress);
        MongoCredential credential = MongoCredential.createCredential("userName","databseName","password".toCharArray());
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(credential);
        //通过连接认证获取MongoDB
        MongoClient mongoClient = new MongoClient(addrs,credentials);
        //连接数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("databaseName");

    }
    public static void linkDB(){
        //连接到mongodb服务
        MongoClient mongoClient = new MongoClient("localhost",27017);
        //连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
        //创建集合
        mongoDatabase.createCollection("test");
        //获取集合
        MongoCollection<Document> collection = mongoDatabase.getCollection("test");
        //插入文档
        /*
        1.创建文档 org.bson.Document 参数为key-value
        2.创建文档集合List<Document>
        3.将文档集合插入数据库集合中
        4.也可以单个插入
         */
        //检索文档——com.mongodb.client.MongoCollection类中的find()方法来获取集合中所有的文档
        /*
        1.获取迭代器FindInterable<Document>
        2.获取游标MongoCursor<Document>
        3.通过游标遍历检索出的文档集合FindIterable<Document> findIterable = collection.find()

         */
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
        //更新文档——updateMany()
        //将likes=100的文档修改为likes=200
        collection.updateMany(Filters.eq("likes",100),new Document("$set",new Document("likes",200)));
        //删除文档
        collection.deleteOne(Filters.eq("likes",200));
        collection.deleteMany(Filters.eq("likes",200));
    }
}
