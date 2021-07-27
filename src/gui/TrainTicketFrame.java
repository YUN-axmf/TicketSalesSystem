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
        new TrainTicketFrame("340103200107111026", "G7262", "2021-07-09", "�Ϸ�", "�Ϻ�");
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
        setLocationRelativeTo(null);                //������ʾ
        setLayout(null);
        setResizable(false);

        setTitle(triNo + "��Ʊ��Ϣ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        triNoLabel = new JLabel(triNo);
        triNoLabel.setBounds(180, 10, 100, 30);

        businessClassLabel = new JLabel("������");
        businessClassLabel.setBounds(80, 60, 50, 30);

        businessFareLabel = new JLabel(String.valueOf(TicketSQL.queryTicFare(triNo, date, "������", origin, destination)) + "Ԫ");
        businessFareLabel.setBounds(130, 60, 50, 30);

        businessField = new JTextField();
        businessField.setText("   " + String.valueOf(TicketSQL.queryTicClass(triNo, date, "������", origin, destination)) + "   ��");
        businessField.setEditable(false);
        businessField.setBounds(230, 60, 80, 30);

        firstClassLabel = new JLabel("һ����");
        firstClassLabel.setBounds(80, 110, 50, 30);

        firstFareLabel = new JLabel(String.valueOf(TicketSQL.queryTicFare(triNo, date, "һ����", origin, destination)) + "Ԫ");
        firstFareLabel.setBounds(130, 110, 50, 30);

        firstField = new JTextField();
        firstField.setText("   " + String.valueOf(TicketSQL.queryTicClass(triNo, date, "һ����", origin, destination)) + "   ��");
        firstField.setEditable(false);
        firstField.setBounds(230, 110, 80, 30);

        secondClassLabel = new JLabel("������");
        secondClassLabel.setBounds(80, 160, 50, 30);

        secondFareLabel = new JLabel(String.valueOf(TicketSQL.queryTicFare(triNo, date, "������", origin, destination)) + "Ԫ");
        secondFareLabel.setBounds(130, 160, 50, 30);

        secondField = new JTextField();
        secondField.setText("   " + String.valueOf(TicketSQL.queryTicClass(triNo, date, "������", origin, destination)) + "   ��");
        secondField.setEditable(false);
        secondField.setBounds(230, 160, 80, 30);

        nullLabel = new JLabel("����");
        nullLabel.setBounds(80, 210, 50, 30);

        nullFareLabel = new JLabel(String.valueOf(TicketSQL.queryTicFare(triNo, date, origin, destination)) + "Ԫ");
        nullFareLabel.setBounds(130, 210, 50, 30);

        nullField = new JTextField();
        nullField.setText("   " + String.valueOf(TicketSQL.queryTicClass(triNo, date, origin, destination)) + "   ��");
        nullField.setEditable(false);
        nullField.setBounds(230, 210, 80, 30);

        classLabel = new JLabel("��λ����");
        classLabel.setBounds(70, 250, 80, 30);

        passIDLabel = new JLabel("�˳������֤��");
        passIDLabel.setBounds(210, 250, 100, 30);

        classCombo = new JComboBox<>();
        classCombo.addItem("");
        classCombo.addItem("������");
        classCombo.addItem("һ����");
        classCombo.addItem("������");
        classCombo.addItem("����");
        classCombo.setBounds(60, 280, 80, 30);

        passIDCombo = new JComboBox<>();
        passIDCombo.addItem("");
        Object[][] passengerInfo = UserSQL.queryPassenger(userID);
        for (int i = 0; i < passengerInfo.length; i++)
            passIDCombo.addItem(String.valueOf(passengerInfo[i][0]));
        passIDCombo.setBounds(180, 280, 160, 30);

        buyButton = new JButton("��Ʊ");
        buyButton.setBounds(100, 330, 80, 30);
        buyButton.addActionListener(new BuyAction());

        returnButton = new JButton("����");
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
                JOptionPane.showMessageDialog(TrainTicketFrame.this, "����ȷѡ��Ʊ��Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (seatClass.equals("������"))
                seatNum = TicketSQL.queryTicClass(triNo, date, "������", origin, destination);
            else if (seatClass.equals("һ����"))
                seatNum = TicketSQL.queryTicClass(triNo, date, "һ����", origin, destination);
            else if (seatClass.equals("������"))
                seatNum = TicketSQL.queryTicClass(triNo, date, "������", origin, destination);
            else if (seatClass.equals("����"))
                seatNum = TicketSQL.queryTicClass(triNo, date, origin, destination);
            if (seatNum == 0)
            {
                JOptionPane.showMessageDialog(TrainTicketFrame.this, "�����ͳ�Ʊ�������㣡", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String ticketNo = "";
            if (seatClass.equals("����"))
                ticketNo = TicketSQL.queryTicNo(triNo, date, origin, destination);
            else
                ticketNo = TicketSQL.queryTicNo(triNo, date, seatClass, origin, destination);
            TicketSQL.buyTicket(ticketNo, userID, passID);
            JOptionPane.showMessageDialog(TrainTicketFrame.this, "��Ʊ�ɹ���");
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
