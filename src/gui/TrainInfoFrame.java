package gui;

import dao.TicketSQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 购票页列车信息类
 */
public class TrainInfoFrame extends JFrame
{
    public static void main(String[] args)
    {
        new TrainInfoFrame("340103200107111026", "合肥", "上海", "2021-07-09", true);
    }

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String[] columnNames = {"车次号", "出发站", "出发时间", "目的站", "到达时间", "历时"};
    private String userID;
    private String origin;
    private String destination;
    private String dateInfo;
    private boolean all;
    private JPanel northPanel;
    private JPanel southPanel;
    private JTable trainTable;
    private JLabel originLabel;
    private JLabel destinationLabel;
    private JLabel label;
    private JTextField dateField;
    private JButton buyButton;
    private JButton returnButton;

    public TrainInfoFrame(String userID, String origin, String destination, String dateInfo, boolean all)
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //居中显示
        setResizable(false);

        setTitle("列车信息");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.userID = userID;
        this.origin = origin;
        this.destination = destination;
        this.dateInfo = dateInfo;
        this.all = all;

        originLabel = new JLabel(origin);
        label = new JLabel("< >");
        destinationLabel = new JLabel(destination);
        dateField = new JTextField(dateInfo);
        dateField.setEditable(false);

        northPanel = new JPanel();
        northPanel.add(originLabel);
        northPanel.add(label);
        northPanel.add(destinationLabel);
        northPanel.add(dateField);

        if (all)
            trainTable = new JTable(TicketSQL.queryTriNo(origin, destination, dateInfo, true), columnNames);
        else
            trainTable = new JTable(TicketSQL.queryTriNo(origin, destination, dateInfo, false), columnNames);
        add(new JScrollPane(trainTable), BorderLayout.CENTER);
        trainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        buyButton = new JButton("购票");
        buyButton.addActionListener(new BuyAction());
        returnButton = new JButton("返回");
        returnButton.addActionListener(new ReturnAction());

        southPanel = new JPanel();
        southPanel.add(buyButton);
        southPanel.add(returnButton);

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public JTable getTrainTable()
    {
        return trainTable;
    }

    private class BuyAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int i = TrainInfoFrame.this.getTrainTable().getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(TrainInfoFrame.this, "请选择要购票的列车信息！");
                return;
            }
            String triNo = TrainInfoFrame.this.getTrainTable().getValueAt(i, 0).toString();
            new TrainTicketFrame(userID, triNo, dateInfo, origin, destination);
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
