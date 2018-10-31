package com.todaysoft.lims.system.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StandardSocketFactory;
import com.todaysoft.lims.system.model.vo.Customer;
import com.todaysoft.lims.system.model.vo.LimsOperationLog;
import com.todaysoft.lims.system.model.vo.User;
import com.todaysoft.lims.system.model.vo.order.Order;
import com.todaysoft.lims.system.model.vo.order.OrderExaminee;
import com.todaysoft.lims.system.model.vo.order.OrderExamineeDiagnosis;
import com.todaysoft.lims.system.model.vo.order.OrderExamineeGene;
import com.todaysoft.lims.system.model.vo.order.OrderExamineePhenotype;
import com.todaysoft.lims.system.model.vo.order.OrderFormRequest;
import com.todaysoft.lims.system.model.vo.order.OrderPrimarySample;
import com.todaysoft.lims.system.model.vo.order.OrderProduct;
import com.todaysoft.lims.system.model.vo.order.OrderSubsidiarySample;
import com.todaysoft.lims.system.service.IOpertaionLogService;
import com.todaysoft.lims.system.service.IOrderService;
import com.todaysoft.lims.system.service.IUserService;
import com.todaysoft.lims.system.service.SystemServiceLog;
import com.todaysoft.lims.utils.Collections3;
import com.todaysoft.lims.utils.StringUtils;

@Aspect  
@Component  
@SuppressWarnings("rawtypes") 
@EnableAspectJAutoProxy
public class SystemLogAspect
{
    @Autowired  
    private IOpertaionLogService logSercice;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IOrderService orderService;
    
    /** 
     * 日志记录 
     */  
    private static final Logger LOGGER = Logger.getLogger(SystemLogAspect.class);  
  
     /** 
      * Service层切点 
      */  
     @Pointcut("@annotation(com.todaysoft.lims.system.service.SystemServiceLog)")      
     public void serviceAspect() {  
           
     }  
     
     private Order order;
     
     @Before("serviceAspect()")    
     public  void doBefore(JoinPoint joinPoint) {    
         LOGGER.info("日志记录操作前");  
         //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
       
          try {  
             //获取日志描述信息  
             String content = getServiceMthodDescription(joinPoint);  
             if("订单管理-修改".equals(content))
             {
                 Object[] args = joinPoint.getArgs();
                 OrderFormRequest or = (OrderFormRequest)args[0];
                 order = orderService.getById(or.getId());
                
             }
         }  catch (Exception e) {    
             LOGGER.error("异常信息:{}", e);  
         } 
    } 
     
     /** 
      * doServiceLog:获取注解参数，记录日志. <br/> 
      * @param joinPoint 切入点参数 
      */  
     @After("serviceAspect()")   
      public  void doServiceLog(JoinPoint joinPoint) {  
         LOGGER.info("日志记录");  
          HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
          // 记录下请求内容
         LOGGER.info("URL : " + request.getRequestURL().toString());
         LOGGER.info("HTTP_METHOD : " + request.getMethod());
         LOGGER.info("IP : " + request.getRemoteAddr());
         LOGGER.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
          //获取管理员用户信息  
         User user = userService.getUserByToken();
           try {  
              //数据库日志  
              LimsOperationLog log = new LimsOperationLog();  
              log.setOperationType(getServiceMthodTableType(joinPoint));  
              //获取日志描述信息  
              String content = getServiceMthodDescription(joinPoint);  
              if("客户管理-启用禁用".equals(content))
              {
                  Object[] args = joinPoint.getArgs();
                  Customer cu = (Customer)args[0];
                  if(0 == cu.getDisableStatus())
                  {
                      content = "客户管理-启用";
                  }
                  else if(1 == cu.getDisableStatus())
                  {
                      content = "客户管理-禁用";
                  }
              }
              if("订单管理-修改".equals(content))
              {
                  StringBuffer s = new StringBuffer();
                  Object[] args = joinPoint.getArgs();
                  OrderFormRequest or = (OrderFormRequest)args[0];
                  Order afterOrder = orderService.getById(or.getId()); 
                  log.setRemarks(JSONObject.toJSONString(afterOrder));
                  LOGGER.info("修改前："+JSONObject.toJSONString(order));
                  LOGGER.info("修改后："+JSONObject.toJSONString(afterOrder));
                  String s1 = compare(order, afterOrder);
                  s.append(s1+",");
                  List<OrderExaminee> oe1List = order.getOrderExamineeList();
                  List<OrderExaminee> oe2List = afterOrder.getOrderExamineeList();
                  if(null != oe1List && oe1List.size() > 0)
                  {
                      OrderExaminee oe1 = oe1List.get(0);
                      List<OrderExamineeDiagnosis> oed1 = oe1.getOrderExamineeDiagnosis();//临床诊断
                      List<OrderExamineePhenotype> oep1 = oe1.getOrderExamineePhenotype();//临床表型
                      List<OrderExamineeGene> oeg1 = oe1.getOrderExamineeGene();//重点关注基因
                      
                      if(null != oe2List && oe2List.size() > 0)
                      {
                          OrderExaminee oe2 = oe2List.get(0);
                          String s2 = compare(oe1,oe2);
                          s.append(s2+",");
                          List<OrderExamineeDiagnosis> oed2 = oe2.getOrderExamineeDiagnosis();
                          //List<String> oedTemp = Lists.newArrayList();
                          String oedStr = "";
                          String oedStr2 = "";
                          List<OrderExamineePhenotype> oep2 = oe2.getOrderExamineePhenotype();//临床表型
                          //List<OrderExamineePhenotype> oepTemp = Lists.newArrayList();
                          String oepStr = "";
                          String oepStr2 = "";
                          List<OrderExamineeGene> oeg2 = oe2.getOrderExamineeGene();//重点关注基因
                          //List<OrderExamineeGene> oegTemp = Lists.newArrayList();
                          String oegStr = "";
                          String oegStr2 = "";
                          if(null != oed1 && oed1.size() > 0)
                          {
                              if(null != oed2 && oed2.size() > 0)
                              {
                                  Iterator<OrderExamineeDiagnosis> it1 = oed1.iterator();
                                  while(it1.hasNext())
                                  {
                                      OrderExamineeDiagnosis orderExamineeDiagnosis = it1.next();
                                      Iterator<OrderExamineeDiagnosis> it2 = oed2.iterator();
                                      while (it2.hasNext())
                                    {
                                          OrderExamineeDiagnosis orderExamineeDiagnosis2 = it2.next();
                                          if(orderExamineeDiagnosis2.getDisease().getId().equals(orderExamineeDiagnosis.getDisease().getId()))
                                          {
                                              it1.remove();
                                              it2.remove();
                                          }
                                    }
                                  }
                              }
                              else
                              {
                                  for (OrderExamineeDiagnosis orderExamineeDiagnosis : oed1)
                                  {
                                        s.append("disease:"+"减少"+orderExamineeDiagnosis.getDisease().getName()+",");
                                        
                                  }
                              }
                          }
                          else
                          {
                              if(null != oed2 && oed2.size() > 0)
                              {
                                  for (OrderExamineeDiagnosis orderExamineeDiagnosis : oed2)
                                {
                                      s.append("disease:"+"增加"+orderExamineeDiagnosis.getDisease().getName()+",");
                                      
                                }
                              }
                          }
                          if(null != oed1 && oed1.size() > 0)
                          {
                              for (OrderExamineeDiagnosis orderExamineeDiagnosis : oed1)
                            {
                                oedStr += orderExamineeDiagnosis.getDisease().getName()+"、";
                            }
                          }
                          if(null != oed2 && oed2.size() > 0)
                          {
                              for (OrderExamineeDiagnosis orderExamineeDiagnosis : oed2)
                            {
                                oedStr2 += orderExamineeDiagnosis.getDisease().getName()+"、";
                            }
                          }
                          if(StringUtils.isNotEmpty(oedStr))
                          {
                              oedStr = oedStr.substring(0, oedStr.length()-1);
                          }
                          if(StringUtils.isNotEmpty(oedStr2))
                          {
                              oedStr2 = oedStr2.substring(0, oedStr2.length()-1);
                          }
                          if(StringUtils.isNotEmpty(oedStr) || StringUtils.isNotEmpty(oedStr2))
                          {
                              s.append("disease:"+oedStr+" 改为  "+oedStr2+",");
                          }
                          
                          
                          //表型
                          if(null != oep1 && oep1.size() > 0)
                          {
                              if(null != oep2 && oep2.size() > 0)
                              {
                                  
                                  Iterator<OrderExamineePhenotype> it1 = oep1.iterator();
                                  while (it1.hasNext())
                                {
                                      OrderExamineePhenotype orderExamineePhenotype = it1.next();
                                      Iterator<OrderExamineePhenotype> it2 = oep2.iterator();
                                      while (it2.hasNext())
                                    {
                                          OrderExamineePhenotype orderExamineePhenotype2 = it2.next();
                                          if(orderExamineePhenotype.getPhenotype().getName().equals(orderExamineePhenotype2.getPhenotype().getName()))
                                          {
                                              it1.remove();
                                              it2.remove();
                                          }
                                    }
                                }
                              }
                              else
                              {
                                  for (OrderExamineePhenotype orderExamineePhenotype : oep1)
                                  {
                                        s.append("phenotype:"+"减少"+orderExamineePhenotype.getPhenotype().getName()+",");
                                        
                                  }
                              }
                          }
                          else
                          {
                              if(null != oep2 && oep2.size() > 0)
                              {
                                  for (OrderExamineePhenotype orderExamineePhenotype : oep2)
                                {
                                      s.append("phenotype:"+"增加"+orderExamineePhenotype.getPhenotype().getName()+",");
                                      
                                }
                              }
                          }
                          if(Collections3.isNotEmpty(oep1))
                          {
                              for (OrderExamineePhenotype orderExamineePhenotype : oep1)
                            {
                                oepStr += orderExamineePhenotype.getPhenotype().getName()+"、";
                            }
                          }
                          if(Collections3.isNotEmpty(oep2))
                          {
                              for (OrderExamineePhenotype orderExamineePhenotype : oep2)
                            {
                                oepStr2 += orderExamineePhenotype.getPhenotype().getName()+"、";
                            }
                          }
                          if(StringUtils.isNotEmpty(oepStr))
                          {
                              oepStr = oepStr.substring(0, oepStr.length()-1);
                          }
                          if(StringUtils.isNotEmpty(oepStr2))
                          {
                              oepStr2 = oepStr2.substring(0, oepStr2.length()-1);
                          }
                         if(StringUtils.isNotEmpty(oepStr) || StringUtils.isNotEmpty(oepStr2))
                         {
                             s.append("phenotype:"+oepStr+" 改为  "+oepStr2+",");
                         }
                          
                          
                          //重点关注基因
                          if(null != oeg1 && oeg1.size() > 0)
                          {
                              if(null != oeg2 && oeg2.size() > 0)
                              {
                                  Iterator<OrderExamineeGene> it1 = oeg1.iterator();
                                  while (it1.hasNext())
                                {
                                      OrderExamineeGene orderExamineeGene = it1.next();
                                      Iterator<OrderExamineeGene> it2 = oeg2.iterator();
                                      while (it2.hasNext())
                                    {
                                          OrderExamineeGene orderExamineeGene2 = it2.next();
                                          if(orderExamineeGene.getGene().getSymbol().equals(orderExamineeGene2.getGene().getSymbol()))
                                          {
                                              it1.remove();
                                              it2.remove();
                                          }
                                    }
                                }
                                 /* for (OrderExamineeGene orderExamineeGene : oeg1)
                                  {
                                        for (OrderExamineeGene orderExamineeGene2 : oeg2)
                                        {
                                            if(!orderExamineeGene.getGene().getSymbol().equals(orderExamineeGene2.getGene().getSymbol()))
                                            {
                                                s.append("gene:"+orderExamineeGene.getGene().getSymbol()+"改为"+orderExamineeGene2.getGene().getSymbol()+",");
                                            }
                                            
                                        }
                                      if(oeg2.contains(orderExamineeGene))
                                      {
                                          oeg2.remove(orderExamineeGene);
                                          oegTemp.add(orderExamineeGene);
                                      }
                                        
                                  }*/
                              }
                              else
                              {
                                  for (OrderExamineeGene orderExamineeGene : oeg1)
                                  {
                                        s.append("gene:"+"减少"+orderExamineeGene.getGene().getSymbol()+",");
                                        
                                  }
                              }
                          }
                          else
                          {
                              if(null != oep2 && oep2.size() > 0)
                              {
                                  for (OrderExamineeGene orderExamineeGene : oeg2)
                                {
                                      s.append("gene:"+"增加"+orderExamineeGene.getGene().getSymbol()+",");
                                      
                                }
                              }
                          }
                         /* if(Collections3.isNotEmpty(oegTemp))
                          {
                              for (OrderExamineeGene orderExamineeGene : oegTemp)
                            {
                                if(oeg1.contains(orderExamineeGene))
                                {
                                    oeg1.remove(orderExamineeGene);
                                }
                            }
                          }*/
                          if(Collections3.isNotEmpty(oeg1))
                          {
                              for (OrderExamineeGene orderExamineeGene : oeg1)
                            {
                                oegStr += orderExamineeGene.getGene().getSymbol()+"、";
                            }
                          }
                          if(Collections3.isNotEmpty(oeg2))
                          {
                              for (OrderExamineeGene orderExamineeGene : oeg2)
                            {
                                oegStr2 += orderExamineeGene.getGene().getSymbol()+"、";
                            }
                          }
                          if(StringUtils.isNotEmpty(oegStr))
                          {
                              oegStr = oegStr.substring(0, oegStr.length()-1);
                          }
                          if(StringUtils.isNotEmpty(oegStr2))
                          {
                              oegStr2 = oegStr2.substring(0, oegStr2.length()-1);
                          }
                          if(StringUtils.isNotEmpty(oegStr) || StringUtils.isNotEmpty(oegStr2))
                          {
                              s.append("gene:"+oegStr+" 改为  "+oegStr2+",");
                          }
                         
                      }
                  }
                //检测产品
                  List<OrderProduct> op1 = order.getOrderProductList();
                  List<OrderProduct> op2 = afterOrder.getOrderProductList();
                  //List<OrderProduct> opTemp = Lists.newArrayList();
                  String opStr = "";
                  String opStr2 = "";
                  if(null != op1 && op1.size() > 0)
                  {
                      if(null != op2 && op2.size() > 0)
                      {
                          Iterator<OrderProduct> it1 = op1.iterator();
                          while (it1.hasNext())
                        {
                              OrderProduct orderProduct = it1.next();
                              Iterator<OrderProduct> it2 = op2.iterator();
                              while (it2.hasNext())
                            {
                                  OrderProduct orderProduct2 = it2.next();
                                  if(orderProduct.getProduct().getId().equals(orderProduct2.getProduct().getId()))
                                  {
                                      it1.remove();
                                      it2.remove();
                                  }
                            }
                        }
                      }
                      else
                      {
                          for (OrderProduct orderProduct : op1)
                        {
                              s.append("product:"+" 减少 "+orderProduct.getProduct().getName()+",");
                        }
                      }
                  }
                  else
                  {
                      if(null != op2 && op2.size() > 0)
                      {
                          for (OrderProduct orderProduct : op2)
                        {
                            s.append("product:"+"增加"+orderProduct.getProduct().getName()+",");
                        }
                      }
                  }
                  if(null != op1 && op1.size() > 0)
                  {
                      for (OrderProduct orderProduct : op1)
                    {
                        opStr += orderProduct.getProduct().getName()+"、";
                    }
                  }
                  if(null != op2 && op2.size() > 0)
                  {
                      for (OrderProduct orderProduct : op2)
                      {
                          opStr2 += orderProduct.getProduct().getName()+"、";
                      }
                  }
                  if(StringUtils.isNotEmpty(opStr))
                  {
                      opStr = opStr.substring(0,opStr.length()-1);
                  }
                  if(StringUtils.isNotEmpty(opStr2))
                  {
                      opStr2 = opStr2.substring(0, opStr2.length()-1);
                  }
                  if(StringUtils.isNotEmpty(opStr) || StringUtils.isNotEmpty(opStr2))
                  {
                      s.append("product:"+opStr+" 改为  "+opStr2+",");
                  }
                  
                  
                  //主样
                  List<OrderPrimarySample> ops1 = order.getOrderPrimarySample();
                  List<OrderPrimarySample> ops2 = afterOrder.getOrderPrimarySample();
                  if(null != ops1 && ops1.size() > 0)
                  {
                      if(null != ops2 && ops2.size() > 0)
                      {
                            for (OrderPrimarySample orderPrimarySample : ops1)
                            {
                                    Iterator iter = ops2.iterator();
                                    while(iter.hasNext()){
                                        OrderPrimarySample orderPrimarySample2 = (OrderPrimarySample)iter.next();
                                        if(orderPrimarySample.getId().equals(orderPrimarySample2.getId()))
                                        {
                                            iter.remove();
                                        }
                                    }
                            }
                        
                      }
                  }
                  if(Collections3.isNotEmpty(ops2))
                  {
                      for (OrderPrimarySample orderPrimarySample : ops2)
                    {
                          s.append("subSampleId:增加 "+orderPrimarySample.getId()+",");
                          s.append("subSampleCode:增加 "+orderPrimarySample.getSampleCode()+",");
                    }
                  }
                //副样
                  List<OrderSubsidiarySample> oss1 = order.getOrderSubsidiarySample();
                  List<OrderSubsidiarySample> oss2 = afterOrder.getOrderSubsidiarySample();
                  if(null != oss1 && oss1.size() > 0)
                  {
                      if(null != oss2 && oss2.size() > 0)
                      {
                            for (OrderSubsidiarySample orderSubsidiarySample : oss1)
                            {
                                Iterator iter = oss2.iterator();
                                while(iter.hasNext()){
                                    OrderSubsidiarySample orderSubsidiarySample2 = (OrderSubsidiarySample)iter.next();
                                    if(orderSubsidiarySample.getId().equals(orderSubsidiarySample2.getId()))
                                    {
                                        String s4 = compare(orderSubsidiarySample, orderSubsidiarySample2);
                                        s.append(s4+",");
                                        iter.remove();
                                    }
                                }
                               /* for (OrderSubsidiarySample orderSubsidiarySample : oss1)
                                {
                                    if(orderSubsidiarySample.getId().equals(orderSubsidiarySample2.getId()))
                                    {
                                        String s4 = compare(orderSubsidiarySample, orderSubsidiarySample2);
                                        s.append(s4+",");
                                    }
                                }
                                if(!oss1.contains(orderSubsidiarySample2))
                                {
                                    s.append("subSampleId:增加 "+orderSubsidiarySample2.getId()+",");
                                    s.append("subSampleCode:增加 "+orderSubsidiarySample2.getSampleCode()+",");
                                }*/
                            
                            }
                          
                      }
                  }
                  if(Collections3.isNotEmpty(oss2))
                  {
                      for (OrderSubsidiarySample orderSubsidiarySample : oss2)
                    {
                          s.append("subSampleId:增加 "+orderSubsidiarySample.getId()+",");
                          s.append("subSampleCode:增加 "+orderSubsidiarySample.getSampleCode()+",");
                    }
                  }
                  log.setRemarks(s.toString());
              }
             /* else if("合同管理待办理-上传".equals(content))
              {
                  Object[] arguments = joinPoint.getArgs();
                  log.setRemarks(JSONObject.toJSON(arguments[2]).toString());
              }*/
              else
              {
                  log.setRemarks(getServiceMthodParams(joinPoint)); 
              }
              
              log.setContent(content);  
              log.setUrl(request.getRequestURL().toString());
              log.setHttpMethod(request.getMethod());
              log.setIp(request.getRemoteAddr());
              log.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
              log.setCreateId(user.getId());  
              log.setCreateName(user.getUsername());
              log.setCreateTime(new Date());  
              logSercice.create(log);  
          }  catch (Exception e) {    
              LOGGER.error("异常信息:{}", e);  
          }      
      }  
     
     /** 
      * getServiceMthodDescription:获取注解中对方法的描述信息 用于service层注解  . <br/> 
      * @param joinPoint 切点  
      * @return 方法描述 
      * @throws Exception  
      */  
     private String getServiceMthodDescription(JoinPoint joinPoint)  
                throws Exception {  
           String targetName = joinPoint.getTarget().getClass().getName();  
           String methodName = joinPoint.getSignature().getName();  
           Object[] arguments = joinPoint.getArgs();  
           Class targetClass = Class.forName(targetName);  
           Method[] methods = targetClass.getMethods();  
           String description = "";  
            for(Method method : methods) {  
                if(method.getName().equals(methodName)) {  
                   Class[] clazzs = method.getParameterTypes();  
                    if(clazzs.length == arguments.length) {  
                       description = method.getAnnotation(SystemServiceLog.class).description();  
                        break;  
                   }  
               }  
           }  
           return description;  
       }  
       
     /** 
      * getServiceMthodTableType:获取注解中对方法的数据表类型 用于service层注解 . <br/> 
      * @param joinPoint 
      * @return 
      * @throws Exception 
      */  
     private int getServiceMthodTableType(JoinPoint joinPoint)  
             throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        int type = 0;  
         for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                 if (clazzs.length == arguments.length) {  
                 type = method.getAnnotation(SystemServiceLog.class).type();  
                     break;  
                }  
            }  
        }  
         return type;  
    }  
       
     /** 
      * getServiceMthodParams:获取json格式的参数. <br/> 
      * @param joinPoint 
      * @return 
      * @throws Exception 
      */  
     private String getServiceMthodParams(JoinPoint joinPoint)  
             throws Exception {  
        Object[] arguments = joinPoint.getArgs(); 
        String params = JSONObject.toJSON(arguments).toString();
        return params;  
    }
     
     /**
      * 比较实体
      * @param model
      * @param model2
      */
     public static String compare (Object model,Object model2) {
         StringBuffer sb = new StringBuffer();
         Class cls = model.getClass();
         Class cls2 = model2.getClass();
         Field[] fields = cls.getDeclaredFields();
         System.out.println("###################### " + model.getClass().getName() + " ####################");
         for (int i =0;i<fields.length;i++) {
             Field field = fields[i];
             char[] buffer = field.getName().toCharArray();
             buffer[0] = Character.toUpperCase(buffer[0]);
             String mothodName = "get" + new String(buffer);
             if(!"fnum".equals(field.getName()) && !"serialVersionUID".equals(field.getName()))
             {
              if(!"orderExamineeList".equals(field.getName()) && !"orderProductList".equals(field.getName()) && !"orderPrimarySample".equals(field.getName()) && !"orderSubsidiarySample".equals(field.getName()) && !"orderExamineeDiagnosis".equals(field.getName()) && !"orderExamineeGene".equals(field.getName()) && !"orderExamineePhenotype".equals(field.getName()))
              {
                  try {
                      Method method = cls.getDeclaredMethod(mothodName);
                      Method method2 = cls2.getDeclaredMethod(mothodName);
                      Object resutl = new Object();
                      Object resutl2 = new Object();
                          resutl  = method.invoke(model, null);
                          resutl2 = method2.invoke(model2, null);
                          if(null != resutl && null != resutl2)
                          {
                              if(!resutl.toString().equals(resutl2.toString()))
                              {
                                  sb.append(field.getName() + "：" + resutl+" 改为  "+resutl2+",");
                                  System.out.println(field.getName() + "：" + resutl+" 改为  "+resutl2);
                              }
                          }
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
              }
         }
         }
         System.out.println("###################### End ####################");
        return sb.toString();
       }
     
     
}
