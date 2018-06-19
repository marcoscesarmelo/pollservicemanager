#language: pt
@Integration @CriacaoEnqueteRestrita
Funcionalidade: Criacao de Enquete para pessoas autorizadas

  Como usuario autorizado, quero criar uma enquete.
  
    Esquema do Cenario: Criacao de enquete
    Dado um usuario autenticado com id "<id>" username "<username>" password "<password>"
    Quando confirmar criacao de enquete
    Entao a enquete deve ser criada
    
  Exemplos:
    |id|username|password|
    |2|danielab|gui123|