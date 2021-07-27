package gui;

import dao.TicketSQL;
import dao.UserSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainTicketFrame extends JFrame
{
    public static void main(String[] args)
    {
        new TrainTicketFrame("340103200107111026", "G7262", "2021-07-09", "合肥", "上海");
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 420;
    private String userID;
    private String triNo;
    private String date;
    private String origin;
    private String destination;
    private JLabel triNoLabel;
    private JLabel businessClassLabel;
    private JLabel businessFareLabel;
    private JLabel firstClassLabel;
    private JLabel firstFareLabel;
    private JLabel secondClassLabel;
    private JLabel secondFareLabel;
    private JLabel nullLabel;
    private JLabel nullFareLabel;
    private JTextField businessField;
    private JTextField firstField;
    private JTextField secondField;
    private JTextField nullField;
    private JLabel classLabel;
    private JLabel passIDLabel;
    private JComboBox<String> classCombo;
    private JComboBox<String> passIDCombo;
    private JButton buyButton;
    private JButton returnButton;

    public TrainTicketFrame(String userID, String triNo, String date, String origin, String destination)
    {
        this.userID = userID;
        this.triNo = triNo;
        this.date = date;
        this.origin = origin;
        this.destination = destination;

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setLayout(null);
        setResizable(false);

        setTitle(triNo + "购票信息");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        triNoLabel = new JLabel(triNo);
        triNoLabel.setBounds(180, 10, 100, 30);

        businessClassLabel = new JLabel("商务座");
        businessClassLabel.setBounds(80, 60, 50, 30);

        businessFareLabel = new JLabel(String.valueOf(TicketSQL.queryTicFare(triNo, date, "商务座", origin, destination)) + "元");
        businessFareLabel.setBounds(130, 60, 50, 30);

        businessField = new JTextField();
        businessField.setText("   " + String.valueOf(TicketSQL.queryTicClass(triNo, date, "商务座", origin, destination)) + "   张");
        businessField.setEditable(false);
        businessField.setBounds(230, 60, 80, 30);

        firstClassLabel = new JLabel("一等座");
        firstClassLabel.setBounds(80, 110, 50, 30);

        firstFareLabel = new JLabel(String.valueOf(TicketSQL.queryTicFare(triNo, date, "一等座", origin, destination)) + "元");
        firstFareLabel.setBounds(130, 110, 50, 30);

        firstField = new JTextField();
        firstField.setText("   " + String.valueOf(TicketSQL.queryTicClass(triNo, date, "一等座", origin, destination)) + "   张");
        firstField.setEditable(false);
        firstField.setBounds(230, 110, 80, 30);

        secondClassLabel = new JLabel("二等座");
        secondClassLabel.setBounds(80, 160, 50, 30);

        secondFareLabel = new JLabel(String.valueOf(TicketSQL.queryTicFare(triNo, date, "二等座", origin, destination)) + "元");
        secondFareLabel.setBounds(130, 160, 50, 30);

        secondField = new JTextField();
        secondField.setText("   " + String.valueOf(TicketSQL.queryTicClass(triNo, date, "二等座", origin, destination)) + "   张");
        secondField.setEditable(false);
        secondField.setBounds(230, 160, 80, 30);

        nullLabel = new JLabel("无座");
        nullLabel.setBounds(80, 210, 50, 30);

        nullFareLabel = new JLabel(String.valueOf(TicketSQL.queryTicFare(triNo, date, origin, destination)) + "元");
        nullFareLabel.setBounds(130, 210, 50, 30);

        nullField = new JTextField();
        nullField.setText("   " + String.valueOf(TicketSQL.queryTicClass(triNo, date, origin, destination)) + "   张");
        nullField.setEditable(false);
        nullField.setBounds(230, 210, 80, 30);

        classLabel = new JLabel("座位类型");
        classLabel.setBounds(70, 250, 80, 30);

        passIDLabel = new JLabel("乘车人身份证号");
        passIDLabel.setBounds(210, 250, 100, 30);

        classCombo = new JComboBox<>();
        classCombo.addItem("");
        classCombo.addItem("商务座");
        classCombo.addItem("一等座");
        classCombo.addItem("二等座");
        classCombo.addItem("无座");
        classCombo.setBounds(60, 280, 80, 30);

        passIDCombo = new JComboBox<>();
        passIDCombo.addItem("");
        Object[][] passengerInfo = UserSQL.queryPassenger(userID);
        for (int i = 0; i < passengerInfo.length; i++)
            passIDCombo.addItem(String.valueOf(passengerInfo[i][0]));
        passIDCombo.setBounds(180, 280, 160, 30);

        buyButton = new JButton("购票");
        buyButton.setBounds(100, 330, 80, 30);
        buyButton.addActionListener(new BuyAction());

        returnButton = new JButton("返回");
        returnButton.setBounds(220, 330, 80, 30);
        returnButton.addActionListener(new ReturnAction());

        add(triNoLabel);
        add(businessClassLabel);
        add(businessFareLabel);
        add(firstClassLabel);
        add(firstFareLabel);
        add(secondClassLabel);
        add(secondFareLabel);
        add(nullLabel);
        add(nullFareLabel);
        add(businessField);
        add(firstField);
        add(secondField);
        add(nullField);
        add(classLabel);
        add(classCombo);
        add(passIDLabel);
        add(passIDCombo);
        add(buyButton);
        add(returnButton);

        setVisible(true);
    }

    private class BuyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String seatClass = classCombo.getItemAt(classCombo.getSelectedIndex());
            String passID = passIDCombo.getItemAt(passIDCombo.getSelectedIndex());
            int seatNum = 0;
            if (seatClass.equals("") || passID.equals(""))
            {
                JOptionPane.showMessageDialog(TrainTicketFrame.this, "请正确选择购票信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (seatClass.equals("商务座"))
                seatNum = TicketSQL.queryTicClass(triNo, date, "商务座", origin, destination);
            else if (seatClass.equals("一等座"))
                seatNum = TicketSQL.queryTicClass(triNo, date, "一等座", origin, destination);
            else if (seatClass.equals("二等座"))
                seatNum = TicketSQL.queryTicClass(triNo, date, "二等座", origin, destination);
            else if (seatClass.equals("无座"))
                seatNum = TicketSQL.queryTicClass(triNo, date, origin, destination);
            if (seatNum == 0)
            {
                JOptionPane.showMessageDialog(TrainTicketFrame.this, "该类型车票数量不足！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String ticketNo = "";
            if (seatClass.equals("无座"))
                ticketNo = TicketSQL.queryTicNo(triNo, date, origin, destination);
            else
                ticketNo = TicketSQL.queryTicNo(triNo, date, seatClass, origin, destination);
            TicketSQL.buyTicket(ticketNo, userID, passID);
            JOptionPane.showMessageDialog(TrainTicketFrame.this, "购票成功！");
            dispose();
        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
        }
    }
}
