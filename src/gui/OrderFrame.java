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
    public static final String[] columnNames1 = {"��Ʊ���", "��Ʊʱ��", "��Ʊ�ص�", "�˳������֤��",
            "�˳�������", "Ʊ��", "����վ", "Ŀ��վ", "��������", "����", "�����", "��λ��", "��λ�ȼ�"};
    public static final String[] columnNames2 = {"��Ʊ���", "��Ʊʱ��", "��Ʊ�ص�", "��Ʊ�����֤��",
            "��Ʊ������", "Ʊ��", "����վ", "Ŀ��վ", "��������", "����", "�����", "��λ��", "��λ�ȼ�"};
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
        setLocationRelativeTo(null);                //������ʾ
        setResizable(false);

        setTitle("����");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.userID = userID;

        radioButtonPanel = new JPanel();
        group = new ButtonGroup();

        purchaseRadioButton = new JRadioButton("��֧��");
        group.add(purchaseRadioButton);
        radioButtonPanel.add(purchaseRadioButton);

        selfRadioButton = new JRadioButton("���˳�Ʊ");
        group.add(selfRadioButton);
        radioButtonPanel.add(selfRadioButton);

        queryButton = new JButton("��ѯ");
        queryButton.addActionListener(new QueryAction());

        refundButton = new JButton("��Ʊ");
        refundButton.addActionListener(new RefundAction());
        refundButton.setVisible(false);

        northPanel = new JPanel();
        northPanel.add(radioButtonPanel);
        northPanel.add(queryButton);
        northPanel.add(refundButton);

        orderTable = new JTable();
        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        indexButton = new JButton("��ҳ");
        indexButton.addActionListener(new IndexAction());
        orderButton = new JButton("����");
        orderButton.setBackground(new Color(255, 242, 254));
        passengerButton = new JButton("�˳���");
        passengerButton.addActionListener(new PassengerAction());
        timesButton = new JButton("ʱ�̱�");
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
                        "��ѡ�񶩵����ͣ�", "����", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(OrderFrame.this, "��ѡ��Ҫ��Ʊ�ĳ�Ʊ��Ϣ��");
                return;
            }
            String ticNo = OrderFrame.this.getOrderTable().getValueAt(i, 0).toString();
            UserSQL.refundTicket(ticNo);
            JOptionPane.showMessageDialog(OrderFrame.this, "��Ʊ�ɹ�!");
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
