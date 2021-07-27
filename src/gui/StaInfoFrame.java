package gui;

import dao.StationSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 查找车站信息界面类
 */
public class StaInfoFrame extends JFrame
{
    public static void main(String[] args)
    {
        new StaInfoFrame("大同", 1);
    }

    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 400;
    private JLabel nameLabel;
    private JLabel lonLabel;
    private JLabel latLabel;
    private JTextField nameField;
    private JTextField lonField;
    private JTextField latField;
    private JButton addButton;
    private JButton returnButton;
    private String name;
    private int status; // 0表示修改车站，1表示增加车站

    public StaInfoFrame(String name, int status)
    {
        this.name = name;
        this.status = status;

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setLayout(null);
        setResizable(false);

        setTitle("车站信息");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameLabel = new JLabel("车站名");
        nameLabel.setBounds(60, 80, 80, 30);
        nameField = new JTextField();
        nameField.setBounds(130, 80, 120, 30);

        lonLabel = new JLabel("经度");
        lonLabel.setBounds(60, 150, 80, 30);
        lonField = new JTextField();
        lonField.setBounds(130, 150, 120, 30);

        latLabel = new JLabel("纬度");
        latLabel.setBounds(60, 220, 80, 30);
        latField = new JTextField();
        latField.setBounds(130, 220, 120, 30);

        addButton = new JButton("增加");
        addButton.setBounds(70, 300, 60, 30);
        addButton.addActionListener(new AddAction());

        returnButton = new JButton("返回");
        returnButton.setBounds(170, 300, 60, 30);
        returnButton.addActionListener(new ReturnAction());

        if (status == 1)
        {
            nameField.setText("");
        }
        else if (status == 0)
        {
            nameField.setText(name);
            lonField.setText(String.valueOf(StationSQL.queryLon(name)));
            latField.setText(String.valueOf(StationSQL.queryLat(name)));
            nameField.setEditable(false);
            lonField.setEditable(false);
            latField.setEditable(false);
            addButton.setVisible(false);
            returnButton.setBounds(120, 300, 60, 30);
        }

        add(nameLabel);
        add(lonLabel);
        add(latLabel);
        add(nameField);
        add(lonField);
        add(latField);
        add(addButton);
        add(returnButton);

        setVisible(true);
    }

    /**
     * 判断车站名是否有效
     * @param name 车站名
     * @return 有效为true，无效为false
     */
    public static boolean validStaName(String name)
    {
        boolean valid = true;
        if (name.length() <= 1 || name.length() >= 8)
            valid = false;
        for (int i = 0; i < name.length(); i++)
            if (!String.valueOf(name.charAt(i)).matches("[\u4e00-\u9fa5]"))
            {
                valid = false;
                break;
            }
        return valid;
    }

    /**
     * 判断经度是否有效
     * @param lon 车站经度
     * @return 有效为true，无效为false
     */
    public static boolean validLon(double lon)
    {
        boolean valid = true;
        if (lon < 73 || lon > 136)
            valid = false;
        if (String.valueOf(lon).length() > 9)
            valid = false;
        return valid;
    }

    /**
     * 判断纬度是否有效
     * @param lat 车站纬度
     * @return 有效为true，无效为false
     */
    public static boolean validLat(double lat)
    {
        boolean valid = true;
        if (lat < 3 || lat > 54)
            valid = false;
        if (String.valueOf(lat).length() > 8)
            valid = false;
        return valid;
    }

    /**
     * 判断字符串是否为数字
     * @param s
     * @return 是为true，不是为false
     */
    public static boolean isDigit(String s)
    {
        boolean right = true;
        int pointNum = 0;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '.')
                pointNum++;
        if (pointNum > 1)
            right = false;
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != '.')
            {
                right = false;
                break;
            }
        return right;
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String staName = nameField.getText().trim();
            if (staName.equals("") || lonField.getText().equals("") || latField.getText().equals(""))
            {
                JOptionPane.showMessageDialog(StaInfoFrame.this, "请输入车站信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validStaName(staName))
            {
                JOptionPane.showMessageDialog(StaInfoFrame.this, "请输入有效的站名！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String lon = lonField.getText().trim();
            String lat = latField.getText().trim();
            if (!isDigit(lon) || !isDigit(lat))
            {
                JOptionPane.showMessageDialog(StaInfoFrame.this, "请输入正确的经纬度！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validLon(Double.parseDouble(lon)) || !validLat(Double.parseDouble(lat)))
            {
                System.out.println(333);
                JOptionPane.showMessageDialog(StaInfoFrame.this, "请输入正确的经纬度！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (StationSQL.isStaExisting(staName))
            {
                JOptionPane.showMessageDialog(StaInfoFrame.this, "该站名已存在！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            StationSQL.addStation(staName, Double.parseDouble(lon), Double.parseDouble(lat));
            JOptionPane.showMessageDialog(StaInfoFrame.this, "增加成功!");
            dispose();
            new AdmStaFrame();
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdmStaFrame();
        }
    }
}
