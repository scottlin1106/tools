import javafx.scene.control.CheckBox;
import net.sf.json.JSONObject;
import utils.HttpClientUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class mainTools {

    private static JFrame frame = new JFrame();
    static String date = toolUtil.getDate();

    static String paymentStr = "";
    static String autoStr = "";
    static String sudoStr = "sudo -i \n";
    static String logStr = "tail -f /usr/local/tomcat702/logs/catalina.out";
    static String dateLogStr = logStr + "." + date + ".log";
    static String logSearchStr = "cat /usr/local/tomcat702/logs/catalina.out";
    static String SearchStr = "cat /usr/local/tomcat702/logs/catalina.out";
    static String pstsStr = "\\cp -r /tmp/psts/ contact.properties payname.properties /home/www/webapps/nfwApp/paystat/WEB-INF/classes/";
    static String dfSuccess = "http://www.yhsld.com/autopay/jsp/pay8.jsp";
    static String dfFailure = "http://www.yhsld.com/autopay/jsp/pay11.jsp";

    static boolean isWork = true, isSudo = false;
    static JTextArea inputTextArea, convertTextArea, keywordTextArea, convertNameTextArea;
    static JComboBox dateBox, colBox;
    static JCheckBox sudoCheckBox;
    static int textAreaSize = 16;

    public static void main(String[] args) throws Exception {
        try {
            frame.setTitle("Linux指令");
            frame.setBounds(800, 300, 700, 650);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(false);
            /* 畫面配置 */
            JLabel inputStr = new JLabel("目的資料夾名稱");
            inputStr.setBounds(10, -5, 300, 50);
            frame.add(inputStr);

            inputTextArea = new JTextArea(200, 5);
            inputTextArea.setBounds(150, 10, 500, 100);
            inputTextArea.setFont(new Font("Monospaced", Font.PLAIN, textAreaSize));
            frame.add(inputTextArea);

            JLabel sudoStr = new JLabel("sudo權限");
            sudoStr.setBounds(10, 40, 300, 50);
            frame.add(sudoStr);

            sudoCheckBox = new JCheckBox();
            sudoCheckBox.setBounds(80, 50, 50, 30);
            sudoCheckBox.setSelected(true);
            frame.add(sudoCheckBox);


            JLabel convertStr = new JLabel("代付訂單資訊");
            convertStr.setBounds(10, 230, 300, 50);
            frame.add(convertStr);

            convertTextArea = new JTextArea(200, 5);
            convertTextArea.setBounds(150, 245, 500, 100);
            convertTextArea.setLineWrap(true);
            convertTextArea.setFont(new Font("Monospaced", Font.PLAIN, textAreaSize));
            frame.add(convertTextArea);

            JLabel convertNameStr = new JLabel("代付名稱");
            convertNameStr.setBounds(10, 270, 300, 50);
            frame.add(convertNameStr);

            convertNameTextArea = new JTextArea(200, 5);
            convertNameTextArea.setBounds(10, 315, 120, 30);
            convertNameTextArea.setLineWrap(true);
            convertNameTextArea.setFont(new Font("Monospaced", Font.PLAIN, textAreaSize));
            frame.add(convertNameTextArea);

            JLabel traceDateStr = new JLabel("LOG 日期");
            traceDateStr.setBounds(10, 400, 70, 150);
            frame.add(traceDateStr);


            dateBox = new JComboBox();
            for (int i = 0; i <= 7; i++) {
                dateBox.addItem(toolUtil.getStateDate(i));
            }
            dateBox.setBounds(80, 460, 120, 30);
            frame.add(dateBox);

            JLabel keyWodrStr = new JLabel("LOG 查找關鍵字");
            keyWodrStr.setBounds(210, 460, 120, 30);
            frame.add(keyWodrStr);

            keywordTextArea = new JTextArea(30, 1);
            keywordTextArea.setBounds(320, 460, 120, 30);
            keywordTextArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
            keywordTextArea.setText("");
            frame.add(keywordTextArea);

            JLabel colStr = new JLabel("LOG 查找行数");
            colStr.setBounds(460, 460, 120, 30);
            frame.add(colStr);

            colBox = new JComboBox();
            for (int i = 1; i <= 10; i++) {
                colBox.addItem("" + i);
            }
            colBox.setBounds(550, 460, 120, 30);
            frame.add(colBox);

            JButton paymentBtn = new JButton("支付路徑");
            paymentBtn.setSize(100, 100);
            paymentBtn.setBounds(160, 150, 100, 50);
            frame.add(paymentBtn);

            JButton autopayBtn = new JButton("代付路徑");
            autopayBtn.setSize(100, 100);
            autopayBtn.setBounds(290, 150, 100, 50);
            frame.add(autopayBtn);

            JButton paymentSumBtn = new JButton("支付統計");
            paymentSumBtn.setSize(100, 100);
            paymentSumBtn.setBounds(420, 150, 100, 50);
            frame.add(paymentSumBtn);

            JButton uatLogBtn = new JButton("UAT LOG");
            uatLogBtn.setSize(100, 100);
            uatLogBtn.setBounds(550, 150, 100, 50);
            frame.add(uatLogBtn);

            JButton dfSucBtn = new JButton("更改成功狀態");
            dfSucBtn.setSize(100, 100);
            dfSucBtn.setBounds(230, 370, 120, 50);
            frame.add(dfSucBtn);

            JButton dfFaiBtn = new JButton("更改失敗狀態");
            dfFaiBtn.setSize(100, 100);
            dfFaiBtn.setBounds(450, 370, 120, 50);
            frame.add(dfFaiBtn);

            JButton searchBtn = new JButton("LOG查询指令");
            searchBtn.setSize(100, 100);
            searchBtn.setBounds(250, 515, 200, 50);
            frame.add(searchBtn);

            /* 監聽配置 */
            SimpleListener simpleListener = new SimpleListener();
            SimpleListener2 simpleListener2 = new SimpleListener2();
            paymentBtn.addActionListener(simpleListener2);
            autopayBtn.addActionListener(simpleListener2);
            uatLogBtn.addActionListener(simpleListener);
            searchBtn.addActionListener(simpleListener);
            dfSucBtn.addActionListener(simpleListener);
            dfFaiBtn.addActionListener(simpleListener);
            paymentSumBtn.addActionListener(simpleListener);

            frame.setLayout(null);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class SimpleListener implements ActionListener {
        /**
         * 利用该内部类来监听所有事件源产生的事件
         * 便于处理事件代码模块化
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonName = e.getActionCommand();
            isSudo = sudoCheckBox.isSelected();
            System.out.println("isSudo = " + isSudo);

            if (buttonName.equals("支付統計")) {
                copyStr(pstsStr, isSudo);
            } else if (buttonName.equals("UAT LOG")) {
                copyStr(logStr, isSudo);
            } else if (buttonName.equals("PRD LOG")) {
                copyStr(dateLogStr, isSudo);
            }

            try {
                if (buttonName.equals("更改成功狀態")) {
                    String bodyStr = HttpClientUtil.formPostSkipSSL(dfSuccess, dfOutStr(convertTextArea.getText(), convertNameTextArea.getText()));
                    System.out.println("bodyStr = " + bodyStr);
                    JSONObject jsObj = JSONObject.fromObject(bodyStr);
                    if ("1".equalsIgnoreCase(jsObj.optString("result"))) {
                        JOptionPane.showMessageDialog(frame, "代付状态变更成功，请客户刷新页面查看", "请求返回", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, jsObj.optJSONObject("error").optString("reason"), jsObj.optJSONObject("error").optString("message"), JOptionPane.INFORMATION_MESSAGE);
                    }

                } else if (buttonName.equals("更改失敗狀態")) {
                    String bodyStr = HttpClientUtil.formPostSkipSSL(dfFailure, dfOutStr(convertTextArea.getText(), convertNameTextArea.getText()));
                    JSONObject jsObj = JSONObject.fromObject(bodyStr);
                    if ("0".equalsIgnoreCase(jsObj.optString("result"))) {
                        JOptionPane.showMessageDialog(frame, jsObj.optJSONObject("error").optString("message"), "请求返回", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, bodyStr, "请检查报文内容", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (buttonName.equals("LOG查询指令")) {
                    setLogStr((String) dateBox.getSelectedItem(), keywordTextArea.getText(), (String) colBox.getSelectedItem());
                    if (isWork) {
                        copyStr(logSearchStr, isSudo);
                    }
                }
            } catch (Exception e2) {
                System.out.println(e.toString());
            }
        }
    }

    private static class SimpleListener2 implements ActionListener {
        /**
         * 利用该内部类来监听所有事件源产生的事件
         * 便于处理事件代码模块化
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonName = e.getActionCommand();
            isSudo = sudoCheckBox.isSelected();
            System.out.println("isSudo = " + isSudo);
            String inStr = inputTextArea.getText();
            if (inStr != null && !"null".equalsIgnoreCase(inStr) && !"".equals(inStr)) {
                paymentStr = "\\cp -r /tmp/payment/" + inStr + " /home/www/webapps/nfwApp/bg-payment/jsp \n";
                autoStr = "\\cp -r /tmp/autopay/" + inStr + " /home/www/webapps/nfwApp/bg-autopay/jsp \n";

                if (buttonName.equals("支付路徑")) {
                    copyStr(paymentStr, isSudo);
                } else if (buttonName.equals("代付路徑")) {
                    copyStr(autoStr, isSudo);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "請輸入資料夾名稱", "提示", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    public static Map<String, Object> dfOutStr(String inStr, String dfName) {
        Map<String, Object> postMap = new HashMap<String, Object>();
        try {
            if (inStr.contains(", ") && inStr.contains("=")) {
                Map<String, Object> extMap = toolUtil.result(inStr, ", ", "=");
                if (inStr.contains("ProviderCode")) {
                    String ProviderCode = (String) extMap.get("ProviderCode");
                    dfName = ProviderCode.replaceAll(".sign", "");
                    postMap.put("payname", dfName);
                }
                if ("".equalsIgnoreCase(inStr)) {
                    JOptionPane.showMessageDialog(frame, "转换内容不得为空", "错误", JOptionPane.ERROR_MESSAGE);
                } else if ("".equalsIgnoreCase(dfName)) {
                    JOptionPane.showMessageDialog(frame, "代付名稱不得为空", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<String> list = new ArrayList();
                    list.add("sn");
                    list.add("payId");
                    list.add("withdrawId");
                    list.add("realAmount");
                    list.add("operatorId");
                    list.add("digest");
                    list.add("bankName");
                    list.add("orderNo");

                    System.out.println("extMap = " + extMap);
                    if (extMap.get("OrderAmount") != null && !"null".equalsIgnoreCase((String) extMap.get("OrderAmount")) && !"".equals(extMap.get("OrderAmount"))) {
                        extMap.put("realAmount", extMap.get("OrderAmount"));
                        extMap.remove("OrderAmount");
                    }

                    List<String> keys = new ArrayList<String>(extMap.keySet());
                    for (String str : list) {
                        for (String extStr : keys) {
                            if (str.equalsIgnoreCase(extStr)) {
                                postMap.put(str, extMap.get(extStr));
                            }
                        }
                    }
                }

            } else {
                throw new Exception();
            }

            System.out.println("[postMap] : " + postMap);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "轉換內容請複製commpay字串", "请检查轉換内容", JOptionPane.INFORMATION_MESSAGE);
        }
        return postMap;
    }

    public static void setLogStr(String date, String keyword, String col) {
        isWork = true;
        String lsStr = SearchStr + "." + date + ".log";
        boolean isapn = false;
        if (!(null == keyword || "".equalsIgnoreCase(keyword) || "null".equalsIgnoreCase(keyword))) {
            if (keyword.length() > 7) {
                String year = "20" + keyword.substring(0, 2);
                String month = keyword.substring(2, 4);
                String day = keyword.substring(4, 6);
                String vDate = year + "-" + month + "-" + day;

                if (toolUtil.validateDate(vDate)) {
                    isapn = true;
                    lsStr = SearchStr + "." + year + "-" + month + "-" + day + ".log | grep -C " + col + " " + keyword;
                } else if (!toolUtil.validateDate(vDate)) {
                    JOptionPane.showMessageDialog(frame, "查找日期超出LOG存檔期限(七天)", "提示", JOptionPane.WARNING_MESSAGE);
                    isWork = false;
                }
            }
            if(!isapn)
            lsStr += " | grep -C " + col + " " + keyword;
        }
        logSearchStr = lsStr;

    }

    public static void copyStr(String str, boolean chkSudo) {
        if (chkSudo)
            str = sudoStr + str;
        JOptionPane.showMessageDialog(frame, str, "複製內容", JOptionPane.INFORMATION_MESSAGE);
        StringSelection selection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void copyStr(String str) {
        copyStr(str, false);
    }
}