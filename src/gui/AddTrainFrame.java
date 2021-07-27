package gui;

import dao.StationSQL;
import dao.TrainSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * �����г���Ϣ��
 * @author LiYueyun
 */
public class AddTrainFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AddTrainFrame();
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 500;
    private JLabel triNoLabel;
    private JLabel typeLabel;
    private JLabel depTimeLabel;
    private JLabel arrTimeLabel;
    private JLabel dayLabel;
    private JLabel depStaLabel;
    private JLabel terminusLabel;
    private JComboBox<String> depStaCombo;
    private JComboBox<String> terminusCombo;
    private JComboBox<String> triNoCombo;
    private JTextField triNoField;
    private JTextField typeField;
    private JComboBox<Integer> dayCombo;
    private JComboBox<String> hourCombo1;
    private JComboBox<String> minuteCombo1;
    private JLabel hourLabel1;
    private JLabel minuteLabel1;
    private JComboBox<String> hourCombo2;
    private JComboBox<String> minuteCombo2;
    private JLabel hourLabel2;
    private JLabel minuteLabel2;
    private JButton addButton;
    private JButton returnButton;

    public AddTrainFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setLayout(null);
        setResizable(false);

        setTitle("�����г�");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        triNoLabel= new JLabel("���κ�");
        triNoLabel.setBounds(70, 40, 70, 30);
        triNoCombo = new JComboBox<>();
        triNoCombo.setBounds(180, 40, 50, 30);
        triNoCombo.addItem("");
        triNoCombo.addItem("G");
        triNoCombo.addItem("D");
        triNoCombo.addItem("Z");
        triNoField = new JTextField();
        triNoField.setBounds(240, 40, 100, 30);

        typeLabel = new JLabel("�г�����");
        typeLabel.setBounds(70, 90, 70, 30);
        typeField = new JTextField();
        typeField.setBounds(180, 90, 160, 30);
        typeField.setEditable(false);

        depTimeLabel = new JLabel("����ʱ��");
        depTimeLabel.setBounds(70, 140, 70, 30);
        hourCombo1 = new JComboBox<String>();
        hourCombo1.setBounds(180, 140, 50, 30);
        hourLabel1 = new JLabel("ʱ");
        hourLabel1.setBounds(235, 140, 40, 30);
        minuteCombo1 = new JComboBox<String>();
        minuteCombo1.setBounds(270, 140, 50, 30);
        minuteLabel1 = new JLabel("��");
        minuteLabel1.setBounds(325, 140, 40, 30);

        arrTimeLabel = new JLabel("����ʱ��");
        arrTimeLabel.setBounds(70, 190, 70, 30);
        hourCombo2 = new JComboBox<String>();
        hourCombo2.setBounds(180, 190, 50, 30);
        hourLabel2 = new JLabel("ʱ");
        hourLabel2.setBounds(235, 190, 40, 30);
        minuteCombo2 = new JComboBox<String>();
        minuteCombo2.setBounds(270, 190, 50, 30);
        minuteLabel2 = new JLabel("��");
        minuteLabel2.setBounds(325, 190, 40, 30);

        for (int i = 0; i <= 9 ; i++)
        {
            hourCombo1.addItem("0" + i);
            hourCombo2.addItem("0" + i);
            minuteCombo1.addItem("0" + i);
            minuteCombo2.addItem("0" + i);
        }
        for (int i = 10; i <= 23; i++)
        {
            hourCombo1.addItem(i + "");
            hourCombo2.addItem(i + "");
            minuteCombo1.addItem(i + "");
            minuteCombo2.addItem(i + "");
        }
        for (int i = 24; i <= 60; i++)
        {
            minuteCombo1.addItem(i + "");
            minuteCombo2.addItem(i + "");
        }

        dayLabel = new JLabel("��������");
        dayLabel.setBounds(70, 240, 70, 30);
        dayCombo = new JComboBox<>();
        dayCombo.setBounds(180, 240, 160, 30);
        dayCombo.addItem(0);
        dayCombo.addItem(1);
        dayCombo.addItem(2);

        depStaLabel = new JLabel("ʼ��վ");
        depStaLabel.setBounds(70, 290, 50, 30);
        depStaCombo = new JComboBox<>();
        depStaCombo.setBounds(180, 290, 160, 30);
        depStaCombo.addItem("");

        terminusLabel = new JLabel("�յ�վ");
        terminusLabel.setBounds(70, 340, 50, 30);
        terminusCombo = new JComboBox<>();
        terminusCombo.setBounds(180, 340, 160, 30);
        terminusCombo.addItem("");

        String[] staName = StationSQL.queryStaName();
        for (int i = 0; i < staName.length; i++)
        {
            depStaCombo.addItem(staName[i]);
            terminusCombo.addItem(staName[i]);
        }

        addButton = new JButton("�����г�");
        addButton.setBounds(80, 410, 100, 30);
        addButton.addActionListener(new AddAction());

        returnButton = new JButton("������һ��");
        returnButton.setBounds(220, 410, 100, 30);
        returnButton.addActionListener(new ReturnAction());

        triNoCombo.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String type = triNoCombo.getSelectedItem().toString();
                if (type.equals("G"))
                    typeField.setText("����");
                else if (type.equals("D"))
                    typeField.setText("����");
                else if (type.equals("Z"))
                    typeField.setText("����");
                else
                    typeField.setText("");
            }
        });

        add(triNoLabel);
        add(typeLabel);
        add(depTimeLabel);
        add(arrTimeLabel);
        add(dayLabel);
        add(depStaLabel);
        add(terminusLabel);
        add(depStaCombo);
        add(terminusCombo);
        add(triNoCombo);
        add(triNoField);
        add(typeField);
        add(dayCombo);
        add(addButton);
        add(returnButton);
        add(hourCombo1);
        add(minuteCombo1);
        add(hourLabel1);
        add(minuteLabel1);
        add(hourCombo2);
        add(minuteCombo2);
        add(hourLabel2);
        add(minuteLabel2);

        setVisible(true);
    }

    public static boolean validTriNo(String triNo)
    {
        boolean valid = true;
        if (triNo.length() > 6)
            valid = false;
        for (int i = 1; i < triNo.length(); i++)
            if (!Character.isDigit(triNo.charAt(i)))
            {
                System.out.println(triNo.charAt(i));
                valid = false;
                break;
            }
        return valid;
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String triType = triNoCombo.getSelectedItem().toString().trim();
            String triNo = triNoField.getText();
            String type = typeField.getText();
            String depSta = depStaCombo.getSelectedItem().toString();
            String terminus = terminusCombo.getSelectedItem().toString();
            if (triType.equals("") || triNo.equals("") || depSta.equals("") || terminus.equals(""))
            {
                JOptionPane.showMessageDialog(AddTrainFrame.this,
                        "��������д�г���Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (depSta.equals(terminus))
            {
                JOptionPane.showMessageDialog(AddTrainFrame.this,
                    "ʼ��վ���յ�վ������ͬ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            triNo = triType + triNo;
            String hour1 = hourCombo1.getSelectedItem().toString();
            String hour2 = hourCombo2.getSelectedItem().toString();
            String minute1 = minuteCombo1.getSelectedItem().toString();
            String minute2 = minuteCombo2.getSelectedItem().toString();
            String depTime = hour1 + ":" + minute1 + ":00";
            String arrTime = hour2 + ":" + minute2 + ":00";
            int days = Integer.parseInt(dayCombo.getSelectedItem().toString());
            if (!validTriNo(triNo))
            {
                JOptionPane.showMessageDialog(AddTrainFrame.this,
                        "������Ϸ����г��ţ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (TrainSQL.isExistingTrain(triNo))
            {
                JOptionPane.showMessageDialog(AddTrainFrame.this,
                        "���г����Ѿ����ڣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            System.out.println(" " + triNo + " .");
            System.out.println(" " + type + " .");
            System.out.println(" " + depTime + " .");
            System.out.println(" " + arrTime + " .");
            System.out.println(" " + days + " .");
            System.out.println(" " + depSta + " .");
            System.out.println(" " + terminus + " .");
            if (days == 0 && (Integer.parseInt(hour1) == Integer.parseInt(hour2) && Integer.parseInt(minute1) >= Integer.parseInt(minute2)) || Integer.parseInt(hour1) > Integer.parseInt(hour2))
            {
                JOptionPane.showMessageDialog(AddTrainFrame.this,
                        "����ʱ����С�ڵ���ʱ�䣡", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            TrainSQL.addTrain(triNo, type, depTime, arrTime, days, depSta, terminus);
            JOptionPane.showMessageDialog(AddTrainFrame.this, "���ӳɹ�!");
            dispose();
            new AdmTrainFrame();
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdmTrainFrame();
        }
    }
}
