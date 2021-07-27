package gui;

import dao.StationSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ���ҳ�վ��Ϣ������
 */
public class StaInfoFrame extends JFrame
{
    public static void main(String[] args)
    {
        new StaInfoFrame("��ͬ", 1);
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
    private int status; // 0��ʾ�޸ĳ�վ��1��ʾ���ӳ�վ

    public StaInfoFrame(String name, int status)
    {
        this.name = name;
        this.status = status;

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setLayout(null);
        setResizable(false);

        setTitle("��վ��Ϣ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nameLabel = new JLabel("��վ��");
        nameLabel.setBounds(60, 80, 80, 30);
        nameField = new JTextField();
        nameField.setBounds(130, 80, 120, 30);

        lonLabel = new JLabel("����");
        lonLabel.setBounds(60, 150, 80, 30);
        lonField = new JTextField();
        lonField.setBounds(130, 150, 120, 30);

        latLabel = new JLabel("γ��");
        latLabel.setBounds(60, 220, 80, 30);
        latField = new JTextField();
        latField.setBounds(130, 220, 120, 30);

        addButton = new JButton("����");
        addButton.setBounds(70, 300, 60, 30);
        addButton.addActionListener(new AddAction());

        returnButton = new JButton("����");
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
     * �жϳ�վ���Ƿ���Ч
     * @param name ��վ��
     * @return ��ЧΪtrue����ЧΪfalse
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
     * �жϾ����Ƿ���Ч
     * @param lon ��վ����
     * @return ��ЧΪtrue����ЧΪfalse
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
     * �ж�γ���Ƿ���Ч
     * @param lat ��վγ��
     * @return ��ЧΪtrue����ЧΪfalse
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
     * �ж��ַ����Ƿ�Ϊ����
     * @param s
     * @return ��Ϊtrue������Ϊfalse
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
                JOptionPane.showMessageDialog(StaInfoFrame.this, "�����복վ��Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validStaName(staName))
            {
                JOptionPane.showMessageDialog(StaInfoFrame.this, "��������Ч��վ����", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String lon = lonField.getText().trim();
            String lat = latField.getText().trim();
            if (!isDigit(lon) || !isDigit(lat))
            {
                JOptionPane.showMessageDialog(StaInfoFrame.this, "��������ȷ�ľ�γ�ȣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validLon(Double.parseDouble(lon)) || !validLat(Double.parseDouble(lat)))
            {
                System.out.println(333);
                JOptionPane.showMessageDialog(StaInfoFrame.this, "��������ȷ�ľ�γ�ȣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (StationSQL.isStaExisting(staName))
            {
                JOptionPane.showMessageDialog(StaInfoFrame.this, "��վ���Ѵ��ڣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            StationSQL.addStation(staName, Double.parseDouble(lon), Double.parseDouble(lat));
            JOptionPane.showMessageDialog(StaInfoFrame.this, "���ӳɹ�!");
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
