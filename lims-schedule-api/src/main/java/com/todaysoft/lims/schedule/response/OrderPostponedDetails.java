package com.todaysoft.lims.schedule.response;

import java.util.List;

public class OrderPostponedDetails
{
    private String orderId;
    
    private OrderPostponedNode postponedWorstNode;
    
    private List<OrderPostponedNode> postponedNodes;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public OrderPostponedNode getPostponedWorstNode()
    {
        return postponedWorstNode;
    }
    
    public void setPostponedWorstNode(OrderPostponedNode postponedWorstNode)
    {
        this.postponedWorstNode = postponedWorstNode;
    }
    
    public List<OrderPostponedNode> getPostponedNodes()
    {
        return postponedNodes;
    }
    
    public void setPostponedNodes(List<OrderPostponedNode> postponedNodes)
    {
        this.postponedNodes = postponedNodes;
    }
}
