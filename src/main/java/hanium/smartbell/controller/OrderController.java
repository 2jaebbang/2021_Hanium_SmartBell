package hanium.smartbell.controller;

import hanium.smartbell.domain.Order;
import hanium.smartbell.domain.OrderItem;
import hanium.smartbell.repository.OrderSearch;
import hanium.smartbell.service.ItemService;
import hanium.smartbell.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final ItemService itemService;
    private final OrderService orderService;

//    @GetMapping(value = "/items/itemListJson")
//    @ResponseBody
//    public List<Item> items() {
//        List<Item> item = itemService.findItems();
//        return item;
//    }

    @GetMapping(value = "/order")
    public String createOrderForm() {
        return "orders/orderForm";
    }


    @PostMapping(value = "/order")
    public String createOrder(@RequestBody OrderItemForm form) {
        OrderItem orderItem = new OrderItem();

        orderService.order(Long.valueOf(form.getItemId()), form.getTemperature(), form.getSize(), form.getAmount());
        return "orders/orderForm";
    }


    /** 주문 결과 확인 */
    @GetMapping(value = "/orderList")
    public String orderList() {
        return "orders/orderList";
    }


    @GetMapping(value = "/orders/orderListJson")
    @ResponseBody
    public List<Order> orders(OrderSearch orderSearch) {
        List<Order> order = orderService.findOrders(orderSearch);
        return order;
    }


    /** 제조 완료 */
    @PostMapping(value = "/orders/{orderId}/completed")
    public String completeOrder(@PathVariable("orderId") Long orderId) {    //pathvariable : url주소와 맵핑.
        orderService.completeOrder(orderId);
        return "/orders/orderList";
    }

    /** 수령 완료 */
    @PostMapping(value = "/orders/{orderId}/received")
    public String receiveOrder(@PathVariable("orderId") Long orderId) {
        orderService.receiveOrder(orderId);
        return "/orders/orderList";
    }

}
