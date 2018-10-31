package com.todaysoft.lims.product.action;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.MetadataSample;
import com.todaysoft.lims.product.entity.Product;
import com.todaysoft.lims.product.entity.ProductProbe;
import com.todaysoft.lims.product.entity.SimpleProduct;
import com.todaysoft.lims.product.model.ProductProbeConfig;
import com.todaysoft.lims.product.model.ProductSimpleModel;
import com.todaysoft.lims.product.model.request.ProductCreateRequest;
import com.todaysoft.lims.product.model.request.ProductDataListRequest;
import com.todaysoft.lims.product.model.request.ProductListRequest;
import com.todaysoft.lims.product.model.request.ProductPagingRequest;
import com.todaysoft.lims.product.service.IProductService;

@RestController
@RequestMapping("/bcm/product")
public class ProductController
{
    @Autowired
    private IProductService productService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<Product> paging(@RequestBody ProductPagingRequest request)
    {
        Pagination<Product> pagination = productService.paging(request);
        return pagination;
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<Product> list(@RequestBody ProductListRequest request)
    {
        return productService.list(request);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody ProductCreateRequest request)
    {
        String productId = productService.create(request);
        if (StringUtils.isNotEmpty(productId) && request.getIfMade().equals(0) && request.getEnabled().equals(1)) //非定制、可用的才同步ES库
        {
            productService.sendProduct(productId, "create");
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable String id)
    {
        Product p = productService.getProduct(id);
        return p;
    }
    
    @RequestMapping(value = "/unique/{code}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> unique(@PathVariable String code)
    {
        return new ResponseEntity<Boolean>(productService.unique(code), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modifyProduct(@RequestBody ProductCreateRequest request)
    {
        
        String productId = productService.modifyProduct(request);
        
        if (StringUtils.isNotEmpty(productId))
        {
            if (request.getIfMade().equals(0) && request.getEnabled().equals(1)) //非定制的修改
            {
                productService.sendProduct(productId, "modify");
            }
            else
            //定制的直接删除
            {
                productService.sendProduct(productId, "delete");
            }
            
        }
    }
    
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public void enable(@RequestBody ProductCreateRequest request)
    {
        String productId = productService.enable(request);
        if (StringUtils.isNotEmpty(productId))
        {
            if (request.getEnabled().equals(0))
            {
                productService.sendProduct(productId, "delete");
            }
            else
            {
                productService.sendProduct(productId, "modify");
            }
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable String id)
    {
        String productId = productService.deleteProduct(id);
        if (StringUtils.isNotEmpty(productId))
        {
            productService.sendProduct(productId, "delete");
        }
    }
    
    @RequestMapping(value = "/contact_products", method = RequestMethod.POST)
    public List<Product> getContactProducts(@RequestBody List<Integer> productIds)
    {
        return productService.getContactProducts(productIds);
    }
    
    @RequestMapping(value = "/productSelect", method = RequestMethod.POST)
    public Pagination<SimpleProduct> productSelect(@RequestBody ProductPagingRequest request)
    {
        Pagination<SimpleProduct> pagination = productService.productSelect(request);
        return pagination;
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody ProductCreateRequest request)
    {
        return productService.validate(request);
    }
    
    @RequestMapping("/getProbeList")
    public List<ProductProbe> getProbeList(@RequestBody ProductCreateRequest request)
    {
        return productService.getProbeList(request);
    }
    
    @RequestMapping("/getProbeDataSizeList")
    public List<Double> getProbeDataSizeByProId(@RequestBody ProductDataListRequest request)
    {
        return productService.getProbeDataSize(request);
    }
    
    @RequestMapping("/getProbeDataSizeByProbeId")
    public Double getProbeDataSizeByProbeId(@RequestBody ProductDataListRequest request)
    {
        return productService.getDefaultDataSize(request);
    }
    
    @RequestMapping("/{pid}/methods/{mid}/probes")
    public List<ProductProbeConfig> getProbeConfigs(@PathVariable String pid, @PathVariable String mid)
    {
        return productService.getProbeConfigs(pid, mid);
    }
    
    @RequestMapping("/{pid}/methods/{mid}/datasize")
    public BigDecimal getTestingDatasize(@PathVariable String pid, @PathVariable String mid)
    {
        return productService.getTestingDatasize(pid, mid);
    }
    
    @RequestMapping("/{pid}/methods/{mid}/coordinates")
    public List<String> getAnalyCoordinates(@PathVariable String pid, @PathVariable String mid)
    {
        return productService.getAnalyCoordinates(pid, mid);
    }
    
    @RequestMapping(value = "/{id}/simple", method = RequestMethod.GET)
    public ProductSimpleModel getProductSimpleModel(@PathVariable String id)
    {
        return productService.getProductSimpleModel(id);
    }
    
    @RequestMapping(value = "/getSampleByProductIds", method = RequestMethod.POST)
    public List<MetadataSample> getSampleByProductIds(@RequestBody ProductListRequest productIds)
    {
        return productService.getSampleByProductIds(productIds);
    }
}
