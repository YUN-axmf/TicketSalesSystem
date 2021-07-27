package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserFrame extends JFrame
{
    public static void main(String[] args)
    {
        new UserFrame("340103200107111026");
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    private String userID = null;
    private JTextField oriStation;
    private JTextField destinationStation;
    private JTextField departDate;
    private JButton transButton;
    private JButton selectSta1;
    private JButton selectSta2;
    private JButton selectData;
    private JCheckBox highSpeed;
    private JButton queryTicket;
    private JSeparator separator1;
    private JSeparator separator2;
    private JPanel panel;
    private JButton indexButton;
    private JButton orderButton;
    private JButton passengerButton;
    private JButton timesButton;
    private Chooser dataChooser;

    public UserFrame(String userID)
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setLayout(null);
        setResizable(false);

        setTitle("首页");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.userID = userID;

        oriStation = new JTextField(5);
        oriStation.setBounds(60, 80, 100, 50);
        oriStation.setEditable(false);

        destinationStation = new JTextField(5);
        destinationStation.setBounds(240, 80, 100, 50);
        destinationStation.setEditable(false);

        transButton = new JButton("");
        transButton.setBounds(190, 100, 20, 20);
        transButton.addActionListener(new TransAction());

        selectSta1 = new JButton("选择车站");
        selectSta1.setBounds(60, 140, 100, 30);
        selectSta1.addActionListener(new SelectSta1Action());
        selectSta2 = new JButton("选择车站");
        selectSta2.setBounds(240, 140, 100, 30);
        selectSta2.addActionListener(new SelectSta2Action());

        separator1 = new JSeparator();
        separator1.setBackground(Color.gray);
        separator1.setBounds(60, 190, 280, 10);

        departDate = new JTextField(10);
        departDate.setBounds(60, 200, 280, 50);
        departDate.setEditable(false);

        dataChooser = Chooser.getInstance();
        dataChooser.register(departDate);

        selectData = new JButton("选择日期");
        selectData.setBounds(240, 210, 100, 30);

        separator2 = new JSeparator();
        separator2.setBackground(Color.gray);
        separator2.setBounds(60, 260, 280, 10);

        highSpeed = new JCheckBox("只看高铁/动车");
        highSpeed.setBounds(60, 270, 200, 30);

        queryTicket = new JButton("查询车票");
        queryTicket.setBounds(60, 310, 280, 50);
        queryTicket.addActionListener(new QueryTicket());

        indexButton = new JButton("首页");
        indexButton.setBackground(new Color(255, 242, 254));
        orderButton = new JButton("订单");
        orderButton.addActionListener(new OrderAction());
        passengerButton = new JButton("乘车人");
        passengerButton.addActionListener(new PassengerAction());
        timesButton = new JButton("时刻表");
        timesButton.addActionListener(new TimesAction());

        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        panel.setBounds(0, 540, 400, 30);
        panel.add(indexButton);
        panel.add(orderButton);
        panel.add(passengerButton);
        panel.add(timesButton);

        add(oriStation);
        add(destinationStation);
        add(departDate);
        add(transButton);
        add(selectSta1);
        add(selectSta2);
        add(separator1);
        add(separator2);
        add(highSpeed);
        add(queryTicket);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * 获取给定日期与当前日期天数之差
     * @param date 给定日期
     * @return 天数差
     */
    public static int getDateDistance(String date) throws ParseException
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long dateTime = simpleDateFormat.parse(date).getTime();
        long today = new Date().getTime();
        int days = (int) ((dateTime - today)/(1000 * 60 * 60 * 24));
        return days + 1;
    }

    public JCheckBox getHighSpeed()
    {
        return highSpeed;
    }

    public JTextField getOriStation()
    {
        return oriStation;
    }

    public JTextField getDestinationStation()
    {
        return destinationStation;
    }

    public JTextField getDepartDate()
    {
        return departDate;
    }

    private class SelectSta1Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new StationFrame(UserFrame.this);
            //UserFrame.this.dispose();
        }
    }

    private class SelectSta2Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new StationFrame(UserFrame.this, 1);
            //UserFrame.this.dispose();
        }
    }

    private class TransAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String origin = UserFrame.this.getOriStation().getText();
            String destination = UserFrame.this.getDestinationStation().getText();
            UserFrame.this.getOriStation().setText(destination);
            UserFrame.this.getDestinationStation().setText(origin);
        }
    }

    private class QueryTicket implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String origin = UserFrame.this.getOriStation().getText();
            String destination = UserFrame.this.getDestinationStation().getText();
            String dateInfo = UserFrame.this.getDepartDate().getText();
            if (origin.equals("") || destination.equals(""))
            {
                JOptionPane.showMessageDialog(UserFrame.this, "请选择车站！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (dateInfo.equals(""))
            {
                JOptionPane.showMessageDialog(UserFrame.this, "请选择日期！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int days = 0;
            try
            {
                days = getDateDistance(dateInfo);
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
            }
            if (days <= 0 || days > 11)
            {
                JOptionPane.showMessageDialog(UserFrame.this, "系统提前11天售票\n请正确选择可售票日期", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (UserFrame.this.getHighSpeed().isSelected())
                new TrainInfoFrame(userID, origin, destination, dateInfo, false);
            else
                new TrainInfoFrame(userID, origin, destination, dateInfo, true);
        }
    }

    private class OrderAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new OrderFrame(userID);
            UserFrame.this.dispose();
        }
    }

    private class PassengerAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new PassengerFrame(userID);
            UserFrame.this.dispose();
        }
    }

    private class TimesAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new TimesFrame(userID);
            UserFrame.this.dispose();
        }
    }
}
