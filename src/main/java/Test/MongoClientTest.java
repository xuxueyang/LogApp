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
        //ͨ��������֤��ȡMongoDB
        MongoClient mongoClient = new MongoClient(addrs,credentials);
        //�������ݿ�
        MongoDatabase mongoDatabase = mongoClient.getDatabase("databaseName");

    }
    public static void linkDB(){
        //���ӵ�mongodb����
        MongoClient mongoClient = new MongoClient("localhost",27017);
        //���ӵ����ݿ�
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mycol");
        //��������
        mongoDatabase.createCollection("test");
        //��ȡ����
        MongoCollection<Document> collection = mongoDatabase.getCollection("test");
        //�����ĵ�
        /*
        1.�����ĵ� org.bson.Document ����Ϊkey-value
        2.�����ĵ�����List<Document>
        3.���ĵ����ϲ������ݿ⼯����
        4.Ҳ���Ե�������
         */
        //�����ĵ�����com.mongodb.client.MongoCollection���е�find()��������ȡ���������е��ĵ�
        /*
        1.��ȡ������FindInterable<Document>
        2.��ȡ�α�MongoCursor<Document>
        3.ͨ���α�������������ĵ�����FindIterable<Document> findIterable = collection.find()

         */
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
        //�����ĵ�����updateMany()
        //��likes=100���ĵ��޸�Ϊlikes=200
        collection.updateMany(Filters.eq("likes",100),new Document("$set",new Document("likes",200)));
        //ɾ���ĵ�
        collection.deleteOne(Filters.eq("likes",200));
        collection.deleteMany(Filters.eq("likes",200));
    }
}
