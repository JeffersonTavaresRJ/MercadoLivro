package com.mercadolivro.events

import com.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
    source: Any,/*quem está chamado o evento..*/
    purchaseModel: PurchaseModel
): ApplicationEvent(source) {

}