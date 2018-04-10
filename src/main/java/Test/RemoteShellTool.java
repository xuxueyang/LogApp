package Test;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

/**
 * Զ��Shell�ű�ִ�й���
 *
 * @author Administrator
 */
public class RemoteShellTool {

    private Connection conn;
    private String ipAddr;
    private String charset = Charset.defaultCharset().toString();
    private String userName;
    private String password;

    public RemoteShellTool(String ipAddr, String userName, String password,
                           String charset) {
        this.ipAddr = ipAddr;
        this.userName = userName;
        this.password = password;
        if (charset != null) {
            this.charset = charset;
        }
    }

    /**
     * ��¼Զ��Linux����
     *
     * @return
     * @throws IOException
     */
    public boolean login() throws IOException {
        conn = new Connection(ipAddr);
        conn.connect(); // ����
        return conn.authenticateWithPassword(userName, password); // ��֤
    }

    /**
     * ִ��Shell�ű�������
     *
     * @param cmds
     *            ����������
     * @return
     */
    public String exec(String cmds) {
        InputStream in = null;
        String result = "";
        try {
            if (this.login()) {
                Session session = conn.openSession(); // ��һ���Ự
                session.execCommand(cmds);
                in = session.getStdout();
                result = this.processStdout(in, this.charset);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            conn.close();
        }
        return result;
    }

    /**
     * ��������ȡ�ַ�����Ϣ
     *
     * @param in
     *            ����������
     * @param charset
     *            �ַ���
     * @return
     */
    public String processStdout(InputStream in, String charset) {
        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while (in.read(buf) != -1) {
                sb.append(new String(buf, charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        RemoteShellTool rms = new RemoteShellTool("47.106.140.252", "root", "Xuxueyang1", null);

        String result = rms.exec("date");
        System.out.println(result);
        result = rms.exec("dir -ls");
        System.out.println(result);
    }
}