package cn.tedu.cart.service;

import cn.tedu.cart.mapper.CartMapper;
import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartService {
    @Autowired(required = false)
    private CartMapper cartMapper;
    public List<Cart> queryMyCarts(String userId) {
        return cartMapper.selectCartsByUserid(userId);
    }
    @Autowired
    private RestTemplate restTemplate;
    public void addCart(Cart cart) {
        /* cart userId productId num
            1.利用userId和productId查询已存在cart,判断为空?
            2.为空 insert
                2.1调用商品微服务
                2.2封装好cart对象 productImage productName productPrice
                2.3 insert
            3.不为空 update
                3.1将数据库num+新增num
                3.2更新数据
         */
        //查询已存在
        Cart exist=cartMapper.selectCartByUserIdAndProductid(cart);
        if(exist==null){
            System.out.println("---------");
            System.out.println(cart.getProductId());
            System.out.println("-----------");
            //insert新增 调用微服务
            String url="http://service-product/product/manage/item/"
                    +cart.getProductId();
            Product product=restTemplate
                    .getForObject(url,
                            Product.class);
            System.out.println("--------"+product);
            cart.setProductName(product.getProductName());
            cart.setProductImage(product.getProductImgurl());
            cart.setProductPrice(product.getProductPrice());
            cartMapper.insertCart(cart);
        }else{
            //已有该购物车
            //update t_cart set num=#{num} where user_id= and product_id=
            cart.setNum(cart.getNum()+exist.getNum());
            cartMapper.updateNumByUseridAndProductid(cart);
        }
    }

    public void updateCartNum(Cart cart) {
        cartMapper.updateNumByUseridAndProductid(cart);
    }

    public void deleteCart(Cart cart) {
        cartMapper.deleteCartByUseridAndProductid(cart);
    }
}
