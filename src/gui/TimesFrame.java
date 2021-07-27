package gui;

import dao.TrainSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

public class TimesFrame extends JFrame
{
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 600;
    private String userID = null;
    public static final String[] columnNames = {"վ��", "վ��", "����ʱ��", "ͣ��ʱ��", "����ʱ��", "���"};
    private JPanel nouthPanel;
    private JPanel southPanel;
    private JButton indexButton;
    private JButton orderButton;
    private JButton passengerButton;
    private JButton timesButton;
    private JLabel trainLabel;
    private JComboBox<String> triNoCombo;
    private JButton queryButton;
    private JTable transitTable;

    public TimesFrame(String userID)
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);

        setTitle("ʱ�̱�");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.userID = userID;

        nouthPanel = new JPanel();
        trainLabel = new JLabel("���κ�");

        triNoCombo = new JComboBox<>();
        triNoCombo.addItem("");
        String[] triNo = TrainSQL.queryTrainNo();
        for (int i = 0; i < triNo.length; i++)
            triNoCombo.addItem(triNo[i]);

        queryButton = new JButton("��ѯ");
        queryButton.addActionListener(new QueryAction());

        nouthPanel.add(trainLabel);
        nouthPanel.add(triNoCombo);
        nouthPanel.add(queryButton);

        transitTable = new JTable();
        add(new JScrollPane(transitTable), BorderLayout.CENTER);

        indexButton = new JButton("��ҳ");
        indexButton.addActionListener(new IndexAction());
        orderButton = new JButton("����");
        orderButton.addActionListener(new OrderAction());
        passengerButton = new JButton("�˳���");
        passengerButton.addActionListener(new PassengerAction());
        timesButton = new JButton("ʱ�̱�");
        timesButton.setBackground(new Color(255, 242, 254));

        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 4));
        southPanel.setBounds(0, 520, 400, 50);
        southPanel.add(indexButton);
        southPanel.add(orderButton);
        southPanel.add(passengerButton);
        southPanel.add(timesButton);

        add(nouthPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        Object[][] info = TrainSQL.queryTransit(triNoCombo.getItemAt(triNoCombo.getSelectedIndex()));
        for (int i = 0; i < info.length; i++)
            model.addRow(info[i]);
        transitTable.setModel(model);
    }

    private class QueryAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String no = triNoCombo.getItemAt(triNoCombo.getSelectedIndex());
            if (no.equals(""))
            {
                JOptionPane.showMessageDialog(TimesFrame.this, "��ѡ�񳵴κţ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            else
                updateTable();
        }
    }

    private class IndexAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new UserFrame(userID);
            TimesFrame.this.dispose();
        }
    }

    private class OrderAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new OrderFrame(userID);
            TimesFrame.this.dispose();
        }
    }

    private class PassengerAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            new PassengerFrame(userID);
            TimesFrame.this.dispose();
        }
    }
}
