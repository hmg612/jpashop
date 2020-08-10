package jpabookorder.jpashop.service;

import jpabookorder.jpashop.domain.Delivery;
import jpabookorder.jpashop.domain.Member;
import jpabookorder.jpashop.domain.Order;
import jpabookorder.jpashop.domain.OrderItem;
import jpabookorder.jpashop.domain.item.Item;
import jpabookorder.jpashop.repository.ItemRepository;
import jpabookorder.jpashop.repository.MemberRepository;
import jpabookorder.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성 (단순화를 위해 하나의 상품만 주문)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); // Order 엔티티에서 CASCADE로 인해 OrderItem & Delivery 함께 저장

        return order.getId();
    }

    //주문취소
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
/*
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

 */
}
