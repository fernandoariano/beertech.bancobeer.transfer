# beertech.bancobeer.transfer
BeerTech Transferencia 

LEGAGY POC - 1º Desafio
Desafio individual

CONTA-CORRENTE
Um end-point de cadastro de Conta-Corrente deverá ser construído obedecendo as seguintes regras:

- Além do ID (PK), a conta-corrente deverá ter um hash único que a identifique;
- Não poderá haver duas contas-correntes com o mesmo hash;
- As operações deverão agora possuir o "hash" da conta-corrente para identificar onde aquela operação estará sendo feita;
- O end-point de Saldo deverá possuir o "hash" da conta-corrente como parâmetro para obter o Saldo da conta desejada;

TRANSFERÊNCIA:
Um end-point de transferência deverá ser desenvolvido obedecendo as seguintes premissas:

- O objeto de requisição deverá conter o Hash da conta de origem, o Hash da conta de destino e o valor a ser transferido;
- Todo o processo deverá ser construído obedecendo a atual arquitetura e sendo feito de forma assíncrona;
- O usuário realizará a requisição de transferência do mesmo modo que é feita a requisição de Operação, enviando ao RabbitMQ o desejo de transferência, enfileirando esse objeto no Rabbit e, logo em seguida, realizando o consumo deste objeto pelo Serviço para ser processado no end-point de Transferência.
