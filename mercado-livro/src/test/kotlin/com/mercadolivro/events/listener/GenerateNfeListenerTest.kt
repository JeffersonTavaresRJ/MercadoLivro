package com.mercadolivro.events.listener

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.helper.buildPurchase
import com.mercadolivro.service.PurchaseService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.UUID

@ExtendWith(MockKExtension::class)
class GenerateNfeListenerTest{
    @InjectMockKs
    private lateinit var generateNfeListener : GenerateNfeListener;
    @MockK
    private lateinit var purchaseService: PurchaseService;

    @Test
    fun `generate nfe`(){
        val fakePurchase = buildPurchase(nfe=null);
        val fakeNfe = UUID.randomUUID();
        val purchaseExpected = fakePurchase.copy(nfe=fakeNfe.toString());

        /*mocar classes do java ou de outras bibliotecas fora do projeto*/
        mockkStatic(UUID::class);

        every{ UUID.randomUUID() } returns fakeNfe;
        every { purchaseService.update(purchaseExpected) } just runs;

        generateNfeListener.listen(PurchaseEvent(this, fakePurchase));

        verify(exactly = 1){ purchaseService.update(purchaseExpected) }

    }
}