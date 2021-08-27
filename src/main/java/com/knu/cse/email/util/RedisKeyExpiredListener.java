package com.knu.cse.email.util;

import com.knu.cse.classseat.domain.ClassSeat;
import com.knu.cse.errors.NotFoundException;
import com.knu.cse.reservation.domain.Reservation;
import com.knu.cse.reservation.repository.ReservationRepository;
import com.knu.cse.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern){
        String[] key = message.toString().split(",");
//        System.out.println("onMessage pattern " + new String(pattern) + " | " + key[0]);
        if (key[0].equals("reservation")){
//            System.out.println(" reservation 만료 : " + key[1]);
            long lkey = Long.parseLong(key[1]);
            reservationService.unreservedById(lkey);
        }
//        else{
//            System.out.println(" 다른게 redis 만료됨. ");
//        }

    }
}
