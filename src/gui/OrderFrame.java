package gui;

import dao.UserSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderFrame extends JFrame
{
    public static void main(String[] args)
    {
        new OrderFrame("340103200107111026");
    }

    public static final int DEFAULT_WIDTH = 1000;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames1 = {"车票编号", "购票时间", "购票地点", "乘车人身份证号",
            "乘车人姓名", "票价", "出发站", "目的站", "发车日期", "车次", "车厢号", "座位号", "座位等级"};
    public static final String[] columnNames2 = {"车票编号", "购票时间", "购票地点", "购票人身份证号",
            "购票人姓名", "票价", "出发站", "目的站", "发车日期", "车次", "车厢号", "座位号", "座位等级"};
    private String userID = null;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel radioButtonPanel;
    private ButtonGroup group;
    private JRadioButton purchaseRadioButton;
    private JRadioButton selfRadioButton;
    private JButton queryButton;
    private JButton refundButton;
    private JButton indexButton;
    private JButton orderButton;
    private JButton passengerButton;
    private JButton timesButton;
    private JTable orderTable;

    public OrderFrame(String userID)
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setResizable(false);

        setTitle("订单");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.userID = userID;

        radioButtonPanel = new JPanel();
        group = new ButtonGroup();

        purchaseRadioButton = new JRadioButton("已支付");
        group.add(purchaseRadioButton);
        radioButtonPanel.add(purchaseRadioButton);

        selfRadioButton = new JRadioButton("本人车票");
        group.add(selfRadioButton);
        radioButtonPanel.add(selfRadioButton);

        queryButton = new JButton("查询");
        queryButton.addActionListener(new QueryAction());

        refundButton = new JButton("退票");
        refundButton.addActionListener(new RefundAction());
        refundButton.setVisible(false);

        northPanel = new JPanel();
        northPanel.add(radioButtonPanel);
        northPanel.add(queryButton);
        northPanel.add(refundButton);

        orderTable = new JTable();
        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        indexButton = new JButton("首页");
        indexButton.addActionListener(new IndexAction());
        orderButton = new JButton("订单");
        orderButton.setBackground(new Color(255, 242, 254));
        passengerButton = new JButton("乘车人");
        passengerButton.addActionListener(new PassengerAction());
        timesButton = new JButton("时刻表");
        timesButton.addActionListener(new TimesAction());

        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 4));
        southPanel.setBounds(0, 520, 400, 50);
        southPanel.add(indexButton);
        southPanel.add(orderButton);
        southPanel.add(passengerButton);
        southPanel.add(timesButton);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateTable(String type)
    {
        DefaultTableModel model = new DefaultTableModel();
        Object[][] info = null;
        if (type.equals("purchaser"))
        {
            model.setColumnIdentifiers(columnNames1);
            info = UserSQL.queryPurchaserTic(userID);
        }
        else if (type.equals("self"))
        {
            model.setColumnIdentifiers(columnNames2);
            info = UserSQL.querySelfTic(userID);
        }
        for (int i = 0; i < info.length; i++)
            model.addRow(info[i]);
        orderTable.setModel(model);
    }

    public JTable getOrderTable()
    {
        return orderTable;
    }

    public JRadioButton getPurchaseRadioButton()
    {
        return purchaseRadioButton;
    }

    public JRadioButton getSelfRadioButton()
    {
        return selfRadioButton;
    }

    public JButton getRefundButton()
    {
        return refundButton;
    }

    private class QueryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (OrderFrame.this.getPurchaseRadioButton().isSelected())
            {
                updateTable("purchaser");
                OrderFrame.this.getRefundButton().setVisible(false);
            }
            else if (OrderFrame.this.getSelfRadioButton().isSelected())
            {
                updateTable("self");
                OrderFrame.this.getRefundButton().setVisible(true);
            }
            else
                JOptionPane.showMessageDialog(OrderFrame.this,
                        "请选择订单类型！", "错误", JOptionPane.WARNING_MESSAGE);
        }
    }

    private class RefundAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = OrderFrame.this.getOrderTable().getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(OrderFrame.this, "请选择要退票的车票信息！");
                return;
            }
            String ticNo = OrderFrame.this.getOrderTable().getValueAt(i, 0).toString();
            UserSQL.refundTicket(ticNo);
            JOptionPane.showMessageDialog(OrderFrame.this, "退票成功!");
            updateTable("self");
        }
    }

    private class IndexAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new UserFrame(userID);
            OrderFrame.this.dispose();
        }
    }

    private class PassengerAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new PassengerFrame(userID);
            OrderFrame.this.dispose();
        }
    }

    private class TimesAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new TimesFrame(userID);
            OrderFrame.this.dispose();
        }
    }
}
