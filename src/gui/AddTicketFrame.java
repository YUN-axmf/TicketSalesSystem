package gui;

import dao.TicketSQL;
import dao.TrainSQL;
import dao.UserSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 增加车票信息界面类
 * @author LiYueyun
 */
public class AddTicketFrame extends JFrame
{
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 650;
    private JLabel ticNoLabel;
    private JLabel fixedTicNo;
    private JLabel faresLabel;
    private JLabel triNoLabel;
    private JLabel dateLabel;
    private JLabel statusLabel;
    private JLabel passIdLabel;
    private JLabel purchaserIdLabel;
    private JLabel coachLabel;
    private JLabel seatLabel;
    private JLabel classLabel;
    private JLabel oriStaLabel;
    private JLabel destinationLabel;
    private JComboBox<String> oriStaCombo;
    private JComboBox<String> oriStaCombo1;
    private JComboBox<String> destinationCombo;
    private JTextField ticNoField;
    private JTextField faresField;
    private JTextField triNoField;
    private JComboBox<String> triNoCombo;
    private JTextField dateField;
    private JComboBox<String> statusCombo;
    private JComboBox<String> passIdCombo;
    private JComboBox<String> purchaserIdCombo;
    private JComboBox<String> coachCombo;
    private JComboBox<String> seatCombo;
    private JTextField classField;
    private JButton addButton;
    private JButton addButton1;
    private JButton returnButton;
    private String date;
    private String triNo;

    public AddTicketFrame(String date)
    {
        this.date = date;

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setLayout(null);
        setResizable(false);

        setTitle("增加车票");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ticNoLabel = new JLabel("车票编号");
        ticNoLabel.setBounds(70, 40, 70, 30);
        fixedTicNo = new JLabel("ZH00000");
        fixedTicNo.setBounds(180, 40, 50, 30);
        ticNoField = new JTextField();
        ticNoField.setBounds(230, 40, 110, 30);

        faresLabel = new JLabel("票价");
        faresLabel.setBounds(70, 90, 70, 30);
        faresField = new JTextField();
        faresField.setBounds(180, 90, 160, 30);

        triNoLabel = new JLabel("车次号");
        triNoLabel.setBounds(70, 140, 70, 30);
        triNoField = new JTextField();
        triNoField.setBounds(180, 140, 160, 30);
        triNoField.setEditable(true);
        triNoField.setVisible(false);
        triNoCombo = new JComboBox<>();
        triNoCombo.setBounds(180, 140, 160, 30);
        triNoCombo.addItem("");
        String[] triNo = TrainSQL.queryTrainNo();
        for (int i = 0; i < triNo.length; i++)
            triNoCombo.addItem(triNo[i]);

        oriStaLabel = new JLabel("出发站");
        oriStaLabel.setBounds(70, 190, 50, 30);
        oriStaCombo = new JComboBox<>();
        oriStaCombo.setBounds(120, 190, 70, 30);
        oriStaCombo.addItem("");
        oriStaCombo1 = new JComboBox<>();
        oriStaCombo1.setBounds(120, 190, 70, 30);
        oriStaCombo1.addItem("");
        oriStaCombo1.setVisible(false);

        destinationLabel = new JLabel("终点站");
        destinationLabel.setBounds(220, 190, 50, 30);
        destinationCombo = new JComboBox<>();
        destinationCombo.setBounds(270, 190, 70, 30);
        destinationCombo.addItem("");

        dateLabel = new JLabel("发车日期");
        dateLabel.setBounds(70, 240, 70, 30);
        dateField = new JTextField();
        dateField.setBounds(180, 240, 160, 30);
        dateField.setText(date);
        dateField.setEditable(false);

        coachLabel = new JLabel("车厢号");
        coachLabel.setBounds(70, 290, 50, 30);
        coachCombo = new JComboBox<>();
        coachCombo.setBounds(120, 290, 70, 30);
        String[] coachNo = TrainSQL.queryCoach();
        coachCombo.addItem("");
        for (int i = 0; i < coachNo.length; i++)
            coachCombo.addItem(coachNo[i]);

        seatLabel = new JLabel("座位号");
        seatLabel.setBounds(220, 290, 50, 30);
        seatCombo = new JComboBox<>();
        seatCombo.setBounds(270, 290, 70, 30);
        String[] seatNo = TrainSQL.querySeat();
        seatCombo.addItem("");
        for (int i = 0; i < seatNo.length; i++)
            seatCombo.addItem(seatNo[i]);

        classLabel = new JLabel("座位等级");
        classLabel.setBounds(70, 340, 70, 30);
        classField = new JTextField();
        classField.setText("无座");
        classField.setEnabled(false);
        classField.setBounds(180, 340, 160, 30);

        statusLabel = new JLabel("车票状态");
        statusLabel.setBounds(70, 390, 70, 30);
        statusCombo = new JComboBox<>();
        statusCombo.setBounds(180, 390, 160, 30);
        statusCombo.addItem("未售");
        statusCombo.addItem("已售");

        passIdLabel = new JLabel("乘车人身份证号");
        passIdLabel.setBounds(70, 440, 100, 30);
        passIdCombo = new JComboBox<>();
        passIdCombo.setBounds(180, 440, 160, 30);
        passIdCombo.addItem("");
        String[] passId = UserSQL.queryPassengerId();
        for (int i = 0; i < passId.length; i++)
            passIdCombo.addItem(passId[i]);

        purchaserIdLabel = new JLabel("购票人身份证号");
        purchaserIdLabel.setBounds(70, 490, 100, 30);
        purchaserIdCombo = new JComboBox<>();
        purchaserIdCombo.setBounds(180, 490, 160, 30);
        purchaserIdCombo.addItem("");
        String[] userId = UserSQL.queryUserId();
        for (int i = 0; i < userId.length; i++)
            purchaserIdCombo.addItem(userId[i]);

        addButton = new JButton("增加车票");
        addButton.setBounds(80, 560, 100, 30);
        addButton.addActionListener(new AddAction());
        addButton1 = new JButton("增加车票");
        addButton1.setBounds(80, 560, 100, 30);
        addButton1.addActionListener(new AddAction1());
        addButton1.setVisible(false);

        returnButton = new JButton("返回上一级");
        returnButton.setBounds(220, 560, 100, 30);
        returnButton.addActionListener(new ReturnAction());

        triNoCombo.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String triNo = (String)triNoCombo.getSelectedItem();
                oriStaCombo.removeAllItems();
                destinationCombo.removeAllItems();
                destinationCombo.addItem("");
                if (!triNo.equals(""))
                {
                    String[] staName = TrainSQL.queryTransitSta(triNo);
                    for (int i = 0; i < staName.length; i++)
                    {
                        oriStaCombo.addItem(staName[i]);
                    }
                }
            }
        });
        oriStaCombo.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String triNo = (String)triNoCombo.getSelectedItem();
                String oriSta = (String)oriStaCombo.getSelectedItem();
                destinationCombo.removeAllItems();
                destinationCombo.addItem("");
                String[] staName = TrainSQL.queryTransitDesSta(triNo, oriSta);
                for (int i = 0; i < staName.length; i++)
                    destinationCombo.addItem(staName[i]);
            }
        });
        coachCombo.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String coach = coachCombo.getItemAt(coachCombo.getSelectedIndex());
                String seat = seatCombo.getItemAt(seatCombo.getSelectedIndex());
                if (!coach.equals("") && !seat.equals(""))
                    classField.setText(TicketSQL.querySeatClass(coach, seat));
                if (coach.equals("") && seat.equals(""))
                    classField.setText("无座");
                if (classField.getText().equals("") || (coach.equals("") && !seat.equals("")) || (!coach.equals("") && seat.equals("")))
                    classField.setText("无此座位");
            }
        });
        seatCombo.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String coach = coachCombo.getItemAt(coachCombo.getSelectedIndex());
                String seat = seatCombo.getItemAt(seatCombo.getSelectedIndex());
                if (!coach.equals("") && !seat.equals(""))
                    classField.setText(TicketSQL.querySeatClass(coach, seat));
                if (coach.equals("") && seat.equals(""))
                    classField.setText("无座");
                if (classField.getText().equals("") || (coach.equals("") && !seat.equals("")) || (!coach.equals("") && seat.equals("")))
                    classField.setText("无此座位");
            }
        });

        add(ticNoLabel);
        add(faresLabel);
        add(fixedTicNo);
        add(triNoLabel);
        add(dateLabel);
        add(statusLabel);
        add(passIdLabel);
        add(purchaserIdLabel);
        add(coachLabel);
        add(seatLabel);
        add(classLabel);
        add(ticNoField);
        add(triNoCombo);
        add(faresField);
        add(triNoField);
        add(dateField);
        add(statusCombo);
        add(passIdCombo);
        add(purchaserIdCombo);
        add(coachCombo);
        add(seatCombo);
        add(classField);
        add(addButton);
        add(addButton1);
        add(returnButton);
        add(oriStaLabel);
        add(oriStaCombo);
        add(oriStaCombo1);
        add(destinationLabel);
        add(destinationCombo);

        setVisible(true);
    }

    public AddTicketFrame(String date, String triNo)
    {
        this(date);
        this.triNo = triNo;
        triNoCombo.setVisible(false);
        triNoField.setVisible(true);
        triNoField.setText(triNo);
        triNoField.setEditable(false);
        oriStaCombo.setVisible(false);
        oriStaCombo1.setVisible(true);
        addButton.setVisible(false);
        addButton1.setVisible(true);

        String[] staName = TrainSQL.queryTransitSta(triNo);
        for (int i = 0; i < staName.length; i++)
            oriStaCombo1.addItem(staName[i]);

        oriStaCombo1.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                String oriSta = (String)oriStaCombo1.getSelectedItem();
                destinationCombo.removeAllItems();
                destinationCombo.addItem("");
                String[] staName = TrainSQL.queryTransitDesSta(triNo, oriSta);
                for (int i = 0; i < staName.length; i++)
                    destinationCombo.addItem(staName[i]);
            }
        });
    }

    public static boolean validFares(String fares)
    {
        boolean valid = true;
        if (!StaInfoFrame.isDigit(fares) || fares.length() > 6)
            valid = false;
        int num = 0;
        boolean flag = false;
        for (int i = 0; i < fares.length(); i++)
        {
            if (flag)
                num++;
            if (fares.charAt(i) == '.')
                flag = true;
        }
        if (num > 1)
            valid = false;
        return valid;
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String ticNo = ticNoField.getText().trim();
            String fares = faresField.getText().trim();
            triNo = triNoCombo.getItemAt(triNoCombo.getSelectedIndex());
            String coachNo = coachCombo.getItemAt(coachCombo.getSelectedIndex());
            String seatNo = seatCombo.getItemAt(seatCombo.getSelectedIndex());
            String oriSta = (String) oriStaCombo.getSelectedItem();
            String destination = (String) destinationCombo.getSelectedItem();
            if (ticNo.equals("") || fares.equals("") || oriSta.equals("") || destination.equals(""))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "请完整填写车票信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (ticNo.length() > 9)
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "请输入正确的车票编号！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ticNo = fixedTicNo.getText() + ticNo;
            if (TicketSQL.isExistingTicket(ticNo))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "该车票编号已存在！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validFares(fares))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "请输入有效的票价！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (oriSta.equals(destination))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "出发站和目的站不能相同！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (classField.getText().equals("无此座位"))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "请重新选择座位！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String ticStatus = (String) statusCombo.getSelectedItem();
            String seatClass = classField.getText();
            if (ticStatus.equals("未售"))
            {
                if (seatClass.equals("无座"))
                {
                    TicketSQL.addTicket(ticNo, Double.parseDouble(fares), oriSta, destination, date, triNo);
                }
                else
                {
                    if (!TicketSQL.validSeat(triNo, date, Integer.parseInt(coachNo), seatNo))
                    {
                        JOptionPane.showMessageDialog(AddTicketFrame.this,
                                "该座位已存在车票！", "错误", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    TicketSQL.addTicket(ticNo, Double.parseDouble(fares), oriSta, destination, date, triNo, Integer.parseInt(coachNo), seatNo, seatClass);
                }
                JOptionPane.showMessageDialog(AddTicketFrame.this, "增加成功!");
                dispose();
                new AdmTicketFrame();
            }
            else if (ticStatus.equals("已售"))
            {
                String passId = (String) passIdCombo.getSelectedItem();
                String purchaserId = (String) purchaserIdCombo.getSelectedItem();
                if (passId.equals("") || purchaserId.equals(""))
                {
                    JOptionPane.showMessageDialog(AddTicketFrame.this,
                            "请选择乘车人和购票人！", "错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (seatClass.equals("无座"))
                    TicketSQL.addTicket(ticNo, Double.parseDouble(fares), oriSta, destination, date, triNo);
                else
                {
                    if (!TicketSQL.validSeat(triNo, date, Integer.parseInt(coachNo), seatNo))
                    {
                        JOptionPane.showMessageDialog(AddTicketFrame.this,
                                "该座位已存在车票！", "错误", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    TicketSQL.addTicket(ticNo, Double.parseDouble(fares), oriSta, destination, date, triNo, Integer.parseInt(coachNo), seatNo, seatClass);
                }
                    TicketSQL.addPurchase(ticNo, purchaserId, passId);
                JOptionPane.showMessageDialog(AddTicketFrame.this, "增加成功!");
                dispose();
                new AdmTicketFrame();
            }
        }
    }

    private class AddAction1 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String ticNo = ticNoField.getText().trim();
            String fares = faresField.getText().trim();
            String coachNo = coachCombo.getItemAt(coachCombo.getSelectedIndex());
            String seatNo = seatCombo.getItemAt(seatCombo.getSelectedIndex());
            String oriSta = (String) oriStaCombo1.getSelectedItem();
            String destination = (String) destinationCombo.getSelectedItem();
            if (ticNo.equals("") || fares.equals("") || oriSta.equals("") || destination.equals(""))
            {
                System.out.println(222222);
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "请完整填写车票信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (ticNo.length() > 9)
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "请输入正确的车票编号！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ticNo = fixedTicNo.getText() + ticNo;
            if (TicketSQL.isExistingTicket(ticNo))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "该车票编号已存在！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!validFares(fares))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "请输入有效的票价！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (oriSta.equals(destination))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "出发站和目的站不能相同！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (classField.getText().equals("无此座位"))
            {
                JOptionPane.showMessageDialog(AddTicketFrame.this,
                        "请重新选择座位！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String ticStatus = (String) statusCombo.getSelectedItem();
            String seatClass = classField.getText();
            if (ticStatus.equals("未售"))
            {
                if (seatClass.equals("无座"))
                {
                    TicketSQL.addTicket(ticNo, Double.parseDouble(fares), oriSta, destination, date, triNo);
                }
                else
                {
                    if (!TicketSQL.validSeat(triNo, date, Integer.parseInt(coachNo), seatNo))
                    {
                        JOptionPane.showMessageDialog(AddTicketFrame.this,
                                "该座位已存在车票！", "错误", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    TicketSQL.addTicket(ticNo, Double.parseDouble(fares), oriSta, destination, date, triNo, Integer.parseInt(coachNo), seatNo, seatClass);
                }
                JOptionPane.showMessageDialog(AddTicketFrame.this, "增加成功!");
                dispose();
                new AdmTicketFrame();
            }
            else if (ticStatus.equals("已售"))
            {
                String passId = (String) passIdCombo.getSelectedItem();
                String purchaserId = (String) purchaserIdCombo.getSelectedItem();
                if (passId.equals("") || purchaserId.equals(""))
                {
                    JOptionPane.showMessageDialog(AddTicketFrame.this,
                            "请选择乘车人和购票人！", "错误", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (seatClass.equals("无座"))
                    TicketSQL.addTicket(ticNo, Double.parseDouble(fares), oriSta, destination, date, triNo);
                else
                {
                    if (!TicketSQL.validSeat(triNo, date, Integer.parseInt(coachNo), seatNo))
                    {
                        JOptionPane.showMessageDialog(AddTicketFrame.this,
                                "该座位已存在车票！", "错误", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    TicketSQL.addTicket(ticNo, Double.parseDouble(fares), oriSta, destination, date, triNo, Integer.parseInt(coachNo), seatNo, seatClass);
                }
                TicketSQL.addPurchase(ticNo, purchaserId, passId);
                JOptionPane.showMessageDialog(AddTicketFrame.this, "增加成功!");
                dispose();
                new AdmTicketFrame();
            }
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdmTicketFrame();
        }
    }
}
