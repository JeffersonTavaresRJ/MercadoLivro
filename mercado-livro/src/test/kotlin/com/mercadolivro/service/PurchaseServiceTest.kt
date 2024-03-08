package com.mercadolivro.service

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.helper.buildPurchase
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher

@ExtendWith(MockKExtension::class)
class PurchaseServiceTest{
    @MockK
    private lateinit var purchaseRepository: PurchaseRepository;
    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var purchaseService: PurchaseService;

    /*variável que atua como uma "caixa" do tipo informado: PurchaseEvent */
    val purchaseEventSlot = slot<PurchaseEvent>();

    @Test
    fun `create purchase`(){
        val fakePurchase = buildPurchase();
        every {  purchaseRepository.save((fakePurchase)) } returns fakePurchase;
        every {  applicationEventPublisher.publishEvent(any()) } returns Unit;

        purchaseService.create(fakePurchase);

        verify(exactly = 1) { purchaseRepository.save((fakePurchase)) };
        verify(exactly = 1) { applicationEventPublisher.publishEvent(capture(purchaseEventSlot)) };

        /*testa se o model criado é igual ao capturado, que é utilizado no event real*/
        assertEquals(fakePurchase, purchaseEventSlot.captured.purchaseModel);
    }

    @Test
    fun `update purchase`(){
        val fakePurchase = buildPurchase();
        every {  purchaseRepository.save((fakePurchase)) } returns fakePurchase;

        purchaseService.update(fakePurchase);

        verify(exactly = 1) { purchaseRepository.save((fakePurchase)) };
    }


}