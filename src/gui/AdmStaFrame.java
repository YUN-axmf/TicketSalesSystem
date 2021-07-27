package gui;

import dao.StationSQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 管理员管理车站界面类
 */
public class AdmStaFrame extends JFrame
{
    public static void main(String[] args)
    {
        new AdmStaFrame();
    }

    public static final int DEFAULT_WIDTH = 350;
    public static final int DEFAULT_HEIGHT = 500;
    public static final String[] columnNames = {"车站名", "经度", "纬度"};
    private JTable stationTable;
    private JPanel northPanel;
    private JPanel southPanel;
    private JTextField field;
    private JButton queryButton;
    private JButton addButton;
    private JButton returnButton;

    public AdmStaFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setResizable(false);

        setTitle("车站管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        field = new JTextField(8);
        queryButton = new JButton("查找");
        queryButton.addActionListener(new QueryAction());
        northPanel = new JPanel();
        northPanel.add(field);
        northPanel.add(queryButton);

        stationTable = new JTable(StationSQL.queryStation(), columnNames);
        add(new JScrollPane(stationTable), BorderLayout.CENTER);
        stationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButton = new JButton("增加车站");
        addButton.addActionListener(new AddAction());
        returnButton = new JButton("返回上一级");
        returnButton.addActionListener(new ReturnAction());

        southPanel = new JPanel();
        southPanel.add(addButton);
        southPanel.add(returnButton);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class QueryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String staName = field.getText().trim();
            if (staName.equals(""))
            {
                JOptionPane.showMessageDialog(AdmStaFrame.this, "请输入要查找的车站信息！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!StationSQL.isStaExisting(staName))
            {
                JOptionPane.showMessageDialog(AdmStaFrame.this, "该车站不存在！", "错误", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new StaInfoFrame(staName, 0);
            dispose();
        }
    }

    private class AddAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new StaInfoFrame("", 1);
            dispose();
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
