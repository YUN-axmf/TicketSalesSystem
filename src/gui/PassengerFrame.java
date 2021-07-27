package gui;

import dao.TrainSQL;
import dao.UserSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassengerFrame extends JFrame
{
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames = {"身份证号", "姓名"};
    private String userID = null;
    private JPanel sorthPanel;
    private JPanel northPanel;
    private JButton indexButton;
    private JButton orderButton;
    private JButton passengerButton;
    private JButton timesButton;
    private JTable passengerTable;
    private JButton addButton;
    private JButton cancelButton;
    private JLabel label;

    public PassengerFrame(String userID)
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setResizable(false);

        setTitle("乘车人");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.userID = userID;

        label = new JLabel("乘车人");
        addButton = new JButton("添加");
        addButton.addActionListener(new AddAction());
        cancelButton = new JButton("删除");
        cancelButton.addActionListener(new CancelAction());

        northPanel = new JPanel();
        northPanel.add(label);
        northPanel.add(addButton);
        northPanel.add(cancelButton);

        passengerTable = new JTable(UserSQL.queryPassenger(userID), columnNames);
        add(new JScrollPane(passengerTable), BorderLayout.CENTER);
        passengerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        indexButton = new JButton("首页");
        indexButton.addActionListener(new IndexAction());
        orderButton = new JButton("订单");
        orderButton.addActionListener(new OrderAction());
        passengerButton = new JButton("乘车人");
        passengerButton.setBackground(new Color(255, 242, 254));
        timesButton = new JButton("时刻表");
        timesButton.addActionListener(new TimesAction());

        sorthPanel = new JPanel();
        sorthPanel.setLayout(new GridLayout(1, 4));
        sorthPanel.setBounds(0, 520, 400, 50);
        sorthPanel.add(indexButton);
        sorthPanel.add(orderButton);
        sorthPanel.add(passengerButton);
        sorthPanel.add(timesButton);

        add(northPanel, BorderLayout.NORTH);
        add(sorthPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        Object[][] info = UserSQL.queryPassenger(userID);
        for (int i = 0; i < info.length; i++)
            model.addRow(info[i]);
        passengerTable.setModel(model);
    }

    public JTable getPassengerTable()
    {
        return passengerTable;
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new AddPassengerFrame(userID, 0);
            dispose();
        }
    }

    private class CancelAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = PassengerFrame.this.getPassengerTable().getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(PassengerFrame.this, "请选择要删除的乘客信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String id = PassengerFrame.this.getPassengerTable().getValueAt(i, 0).toString();
            if (userID.equals(id))
            {
                JOptionPane.showMessageDialog(PassengerFrame.this, "不可删除自身！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            UserSQL.deletePassenger(userID, id);
            JOptionPane.showMessageDialog(PassengerFrame.this, "删除成功!");
            updateTable();
        }
    }

    private class IndexAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new UserFrame(userID);
            PassengerFrame.this.dispose();
        }
    }

    private class OrderAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new OrderFrame(userID);
            PassengerFrame.this.dispose();
        }
    }

    private class TimesAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new TimesFrame(userID);
            PassengerFrame.this.dispose();
        }
    }
}
