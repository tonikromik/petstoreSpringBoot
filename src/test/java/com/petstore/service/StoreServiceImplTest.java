package com.petstore.service;

import static com.petstore.service.impl.StoreServiceImpl.ORDER_NOT_FOUND;
import static com.petstore.testdatafactory.StoreTestFactory.TEST_ORDER;
import static com.petstore.testdatafactory.StoreTestFactory.TEST_ORDER_DTO;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.petstore.dto.OrderDto;
import com.petstore.mapper.OrderMapper;
import com.petstore.repository.StoreRepository;
import com.petstore.service.impl.StoreServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StoreServiceImplTest {
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private OrderMapper ordersMapper;
    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    public void findById_ReturnOrdersDto() {
        when(storeRepository.findAllFieldsById(1L)).thenReturn(Optional.of(TEST_ORDER));
        when(ordersMapper.toDto(TEST_ORDER)).thenReturn(TEST_ORDER_DTO);

        OrderDto orderDto = storeService.findById(1L);

        verify(storeRepository).findAllFieldsById(1L);
        assertNotNull(orderDto);
        assertEquals(TEST_ORDER_DTO, orderDto);
    }

    @Test
    public void findById_ThrowEntityNotFoundException() {
        when(storeRepository.findAllFieldsById(anyLong())).thenThrow(
                new EntityNotFoundException(format(ORDER_NOT_FOUND, 1L)));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> storeService.findById(1L));

        verify(storeRepository).findAllFieldsById(1L);
        assertEquals(format(ORDER_NOT_FOUND, 1L), exception.getMessage());
    }

    @Test
    public void saveOrder_ReturnOrdersDto() {
        when(ordersMapper.toEntity(any())).thenReturn(TEST_ORDER);
        when(storeRepository.save(any())).thenReturn(TEST_ORDER);
        when(ordersMapper.toDto(any())).thenReturn(TEST_ORDER_DTO);

        OrderDto savedOrder = storeService.saveOrder(TEST_ORDER_DTO);

        verify(storeRepository).save(TEST_ORDER);
        assertNotNull(savedOrder);
        assertEquals(TEST_ORDER_DTO, savedOrder);
    }

    @Test
    public void delete_ReturnVoid() {
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(TEST_ORDER));
        doNothing().when(storeRepository).delete(TEST_ORDER);

        assertAll(() -> storeService.delete(1L));
        verify(storeRepository).delete(TEST_ORDER);
    }
}