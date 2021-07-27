package gui;

import dao.TicketSQL;
import dao.TrainSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 管理员管理车票类
 * @author LiYueyun
 */
public class AdmTicketFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AdmTicketFrame();
    }
    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames1 = {"车票编号", "票价", "出发站", "目的站", "发车日期",
            "车次", "乘车人身份证号", "车票状态", "车厢号", "座位号", "座位等级"};
    public static final String[] columnNames2 = {"车票编号", "购票时间", "购票地点", "购票人身份证号",
            "购票人姓名", "票价", "出发站", "目的站", "发车日期", "车次"};
    private JTable ticketTable;
    private JPanel northPanel;
    private JPanel southPanel;
    private JLabel dateLabel;
    private JTextField dateField;
    private JLabel triNoLabel;
    private JComboBox<String> triNoCombo;
    private JPanel radioButtonPanel;
    private ButtonGroup group;
    private JRadioButton purchaseRadioButton;
    private JRadioButton ticketRadioButton;
    private JButton queryButton;
    private JButton modifyButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton statisticsButton;
    private JButton returnButton;

    public AdmTicketFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setResizable(false);
        setLayout(null);

        setTitle("管理车票");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        northPanel = new JPanel();
        northPanel.setBounds(0, 0, DEFAULT_WIDTH, 170);
        northPanel.setLayout(null);

        dateLabel = new JLabel("发车日期");
        dateLabel.setBounds(220, 30, 80, 30);
        dateField = new JTextField();
        dateField.setBounds(310, 30, 150, 30);
        dateField.setEditable(false);
        Chooser dataChooser = Chooser.getInstance();
        dataChooser.register(dateField);

        triNoLabel = new JLabel("车次号");
        triNoLabel.setBounds(220, 80, 80, 30);
        triNoCombo = new JComboBox<>();
        triNoCombo.setBounds(310, 80, 150, 30);
        triNoCombo.addItem("ALL");
        String[] triNo = TrainSQL.queryTrainNo();
        for (int i = 0; i < triNo.length; i++)
            triNoCombo.addItem(triNo[i]);

        queryButton = new JButton("查找");
        queryButton.setBounds(520, 60, 80, 30);
        queryButton.addActionListener(new QueryAction());

        radioButtonPanel = new JPanel();
        group = new ButtonGroup();
        ticketRadioButton = new JRadioButton("车票情况");
        group.add(ticketRadioButton);
        radioButtonPanel.add(ticketRadioButton);
        purchaseRadioButton = new JRadioButton("购票情况");
        group.add(purchaseRadioButton);
        radioButtonPanel.add(purchaseRadioButton);
        radioButtonPanel.setBounds(280, 130, 200, 30);

        northPanel.add(dateLabel);
        northPanel.add(dateField);
        northPanel.add(triNoLabel);
        northPanel.add(triNoCombo);
        northPanel.add(queryButton);
        northPanel.add(radioButtonPanel);

        ticketTable = new JTable();
        JScrollPane pane = new JScrollPane(ticketTable);
        pane.setBounds(0, 170, DEFAULT_WIDTH, 350);
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("增加车票");
        addButton.addActionListener(new AddAction());
        addButton.setVisible(false);
        deleteButton = new JButton("删除车票");
        deleteButton.addActionListener(new DeleteAction());
        deleteButton.setVisible(false);
        modifyButton = new JButton("修改车票");
        modifyButton.addActionListener(new ModifyAction());
        modifyButton.setVisible(false);
        statisticsButton = new JButton("统计车票");
        statisticsButton.addActionListener(new StatisticsAction());
        statisticsButton.setVisible(false);
        returnButton = new JButton("返回上一级");
        returnButton.addActionListener(new ReturnAction());

        southPanel = new JPanel();
        southPanel.setBounds(0, 525, DEFAULT_WIDTH, 90);
        southPanel.add(addButton);
        southPanel.add(deleteButton);
        southPanel.add(modifyButton);
        southPanel.add(statisticsButton);
        southPanel.add(returnButton);

        add(northPanel);
        add(pane);
        add(southPanel);

        setVisible(true);
    }

    public void updateTable(String date, String type)
    {
        DefaultTableModel model = new DefaultTableModel();
        Object[][] info = null;
        if (type.equals("ticket"))
        {
            model.setColumnIdentifiers(columnNames1);
            info = TicketSQL.queryTic(date);
        }
        else if (type.equals("purchase"))
        {
            model.setColumnIdentifiers(columnNames2);
            info = TicketSQL.queryPurchase(date);
        }
        for (int i = 0; i < info.length; i++)
            model.addRow(info[i]);
        ticketTable.setModel(model);
    }

    public void updateTable(String date, String triNo, String type)
    {
        DefaultTableModel model = new DefaultTableModel();
        Object[][] info = null;
        if (type.equals("ticket"))
        {
            model.setColumnIdentifiers(columnNames1);
            info = TicketSQL.queryTic(date, triNo);
        }
        else if (type.equals("purchase"))
        {
            model.setColumnIdentifiers(columnNames2);
            info = TicketSQL.queryPurchase(date, triNo);
        }
        for (int i = 0; i < info.length; i++)
            model.addRow(info[i]);
        ticketTable.setModel(model);
    }

    private class QueryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String date = dateField.getText();
            String triNo = triNoCombo.getItemAt(triNoCombo.getSelectedIndex());
            if (date.equals(""))
            {
                JOptionPane.showMessageDialog(AdmTicketFrame.this,
                        "请选择发车日期！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (AdmTicketFrame.this.ticketRadioButton.isSelected())
            {
                addButton.setVisible(true);
                deleteButton.setVisible(true);
                modifyButton.setVisible(true);
                statisticsButton.setVisible(true);
                if (triNo == "ALL")
                    updateTable(date, "ticket");
                else
                    updateTable(date, triNo, "ticket");
            }
            else if (AdmTicketFrame.this.purchaseRadioButton.isSelected())
            {
                if (triNo == "ALL")
                    updateTable(date, "purchase");
                else
                    updateTable(date, triNo, "purchase");
                addButton.setVisible(false);
                deleteButton.setVisible(false);
                modifyButton.setVisible(false);
                statisticsButton.setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(AdmTicketFrame.this,
                        "请选择查询类型！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String date = dateField.getText();
            String triNo = triNoCombo.getItemAt(triNoCombo.getSelectedIndex());
            if (date.equals(""))
            {
                JOptionPane.showMessageDialog(AdmTicketFrame.this,
                        "请选择发车日期！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (triNo.equals("ALL"))
                new AddTicketFrame(date);
            else
                new AddTicketFrame(date, triNo);
            dispose();
        }
    }

    private class DeleteAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = AdmTicketFrame.this.ticketTable.getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(AdmTicketFrame.this,
                        "请选择要删除的车票信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String ticNo = AdmTicketFrame.this.ticketTable.getValueAt(i, 0).toString();
            TicketSQL.deleteTicket(ticNo);
            JOptionPane.showMessageDialog(AdmTicketFrame.this, "删除成功!");
            dispose();
            new AdmTicketFrame();
        }
    }

    private class ModifyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String date = dateField.getText();
            String triNo = triNoCombo.getItemAt(triNoCombo.getSelectedIndex());
            if (date.equals(""))
            {
                JOptionPane.showMessageDialog(AdmTicketFrame.this,
                        "请选择发车日期！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int i = AdmTicketFrame.this.ticketTable.getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(AdmTicketFrame.this,
                        "请选择要修改的车票信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String ticNo = AdmTicketFrame.this.ticketTable.getValueAt(i, 0).toString();
            new ModifyTicketFrame(ticNo);
            dispose();
        }
    }

    private class StatisticsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String date = dateField.getText();
            String triNo = triNoCombo.getItemAt(triNoCombo.getSelectedIndex());
            if (triNo.equals("ALL"))
            {
                int allNum = TicketSQL.queryAllTicNum(date);
                int emptyNum = TicketSQL.queryTicNum(date);
                int soldNum = allNum - emptyNum;
                double faresSum = TicketSQL.queryTicFares(date);
                String msg = "共有车票" + allNum + "张，售出" + soldNum + "张，剩余" + emptyNum + "张！\n销售额为" + faresSum + "元！" ;
                JOptionPane.showMessageDialog(AdmTicketFrame.this, msg);
            }
            else
            {
                int allNum = TicketSQL.queryAllTicNum(triNo, date);
                int emptyNum = TicketSQL.queryTicNum(triNo, date);
                int soldNum = allNum - emptyNum;
                double faresSum = TicketSQL.queryTicFares(triNo, date);
                String msg = "共有车票" + allNum + "张，售出" + soldNum + "张，剩余" + emptyNum + "张！\n销售额为" + faresSum + "元！" ;
                JOptionPane.showMessageDialog(AdmTicketFrame.this, msg);
            }


        }
    }

    private class ReturnAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            new AdminFrame();
        }
    }
}
