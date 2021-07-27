package gui;

import dao.TicketSQL;
import dao.TrainSQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ����Ա����Ʊ��
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
    public static final String[] columnNames1 = {"��Ʊ���", "Ʊ��", "����վ", "Ŀ��վ", "��������",
            "����", "�˳������֤��", "��Ʊ״̬", "�����", "��λ��", "��λ�ȼ�"};
    public static final String[] columnNames2 = {"��Ʊ���", "��Ʊʱ��", "��Ʊ�ص�", "��Ʊ�����֤��",
            "��Ʊ������", "Ʊ��", "����վ", "Ŀ��վ", "��������", "����"};
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
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);
        setLayout(null);

        setTitle("����Ʊ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        northPanel = new JPanel();
        northPanel.setBounds(0, 0, DEFAULT_WIDTH, 170);
        northPanel.setLayout(null);

        dateLabel = new JLabel("��������");
        dateLabel.setBounds(220, 30, 80, 30);
        dateField = new JTextField();
        dateField.setBounds(310, 30, 150, 30);
        dateField.setEditable(false);
        Chooser dataChooser = Chooser.getInstance();
        dataChooser.register(dateField);

        triNoLabel = new JLabel("���κ�");
        triNoLabel.setBounds(220, 80, 80, 30);
        triNoCombo = new JComboBox<>();
        triNoCombo.setBounds(310, 80, 150, 30);
        triNoCombo.addItem("ALL");
        String[] triNo = TrainSQL.queryTrainNo();
        for (int i = 0; i < triNo.length; i++)
            triNoCombo.addItem(triNo[i]);

        queryButton = new JButton("����");
        queryButton.setBounds(520, 60, 80, 30);
        queryButton.addActionListener(new QueryAction());

        radioButtonPanel = new JPanel();
        group = new ButtonGroup();
        ticketRadioButton = new JRadioButton("��Ʊ���");
        group.add(ticketRadioButton);
        radioButtonPanel.add(ticketRadioButton);
        purchaseRadioButton = new JRadioButton("��Ʊ���");
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

        addButton = new JButton("���ӳ�Ʊ");
        addButton.addActionListener(new AddAction());
        addButton.setVisible(false);
        deleteButton = new JButton("ɾ����Ʊ");
        deleteButton.addActionListener(new DeleteAction());
        deleteButton.setVisible(false);
        modifyButton = new JButton("�޸ĳ�Ʊ");
        modifyButton.addActionListener(new ModifyAction());
        modifyButton.setVisible(false);
        statisticsButton = new JButton("ͳ�Ƴ�Ʊ");
        statisticsButton.addActionListener(new StatisticsAction());
        statisticsButton.setVisible(false);
        returnButton = new JButton("������һ��");
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
                        "��ѡ�񷢳����ڣ�", "����", JOptionPane.WARNING_MESSAGE);
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
                        "��ѡ���ѯ���ͣ�", "����", JOptionPane.WARNING_MESSAGE);
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
                        "��ѡ�񷢳����ڣ�", "����", JOptionPane.WARNING_MESSAGE);
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
                        "��ѡ��Ҫɾ���ĳ�Ʊ��Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String ticNo = AdmTicketFrame.this.ticketTable.getValueAt(i, 0).toString();
            TicketSQL.deleteTicket(ticNo);
            JOptionPane.showMessageDialog(AdmTicketFrame.this, "ɾ���ɹ�!");
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
                        "��ѡ�񷢳����ڣ�", "����", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int i = AdmTicketFrame.this.ticketTable.getSelectedRow();
            if (i == -1)
            {
                JOptionPane.showMessageDialog(AdmTicketFrame.this,
                        "��ѡ��Ҫ�޸ĵĳ�Ʊ��Ϣ��", "����", JOptionPane.WARNING_MESSAGE);
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
                String msg = "���г�Ʊ" + allNum + "�ţ��۳�" + soldNum + "�ţ�ʣ��" + emptyNum + "�ţ�\n���۶�Ϊ" + faresSum + "Ԫ��" ;
                JOptionPane.showMessageDialog(AdmTicketFrame.this, msg);
            }
            else
            {
                int allNum = TicketSQL.queryAllTicNum(triNo, date);
                int emptyNum = TicketSQL.queryTicNum(triNo, date);
                int soldNum = allNum - emptyNum;
                double faresSum = TicketSQL.queryTicFares(triNo, date);
                String msg = "���г�Ʊ" + allNum + "�ţ��۳�" + soldNum + "�ţ�ʣ��" + emptyNum + "�ţ�\n���۶�Ϊ" + faresSum + "Ԫ��" ;
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
