window.addEventListener('load', function(){
var head = document.getElementsByTagName('head')[0];
var header = document.getElementsByTagName('header')[0];

var item = document.createElement("link");	
item.setAttribute("href", "https://unpkg.com/nes.css@latest/css/nes.min.css");
item.setAttribute("rel", "stylesheet");
head.appendChild(item);

item = document.createElement("link");	
item.setAttribute("href", "style.css");
item.setAttribute("rel", "stylesheet");
head.appendChild(item);

item = document.createElement("link");	
item.setAttribute("href", "https://fonts.googleapis.com/css?family=Press+Start+2P&display=swap");
item.setAttribute("rel", "stylesheet");
head.appendChild(item);

item = document.createElement("link");	
item.setAttribute("href", "../common.css");
item.setAttribute("rel", "stylesheet");
head.appendChild(item);

item = document.createElement("meta");
item.setAttribute("charset", "UTF-8");
head.appendChild(item);	

setTimeout(() => {

	item = document.createElement("script");
	item.setAttribute("src", "js.js");
	head.appendChild(item);
	
	item = document.createElement("script");
	item.setAttribute("src", "../commonJquery.js");
	head.appendChild(item);
	
	setTimeout(() => {
		document.getElementById("hContent").classList.remove("hidden");
		document.getElementById("dContent").classList.remove("hidden");
		document.getElementById("dWait").classList.add("hidden");
	}, 100);

}, 500);

header.innerHTML = 
	'<div class="logo">'+
	'	<img src="../img/logo.png"/>'+
	'	<h3>Estacionamento Nishikino</h3>'+
	'</div>'+
	'<nav>		'+
	'	<a class="nes-text is-primary navlink" href="../Proprietario/Proprietario.html">Proprietarios</a>'+
	'	<div id="btnVeiculo" class="navdropdown">'+
	'		<span class="nes-text is-primary navspan">Veiculos</span>'+
	'		<div class="hidden">'+
	'			<a class="nes-text is-primary navlink" href="../Marca/Marca.html">Marcas</a>'+
	'			<a class="nes-text is-primary navlink" href="../Modelo/Modelo.html">Modelos</a>'+
	'			<a class="nes-text is-primary navlink" href="../Veiculo/Veiculo.html">Veiculos</a>'+
	'			<a class="nes-text is-primary navlink" href="../TabelaPreco/TabelaPreco.html">Tabela de Preço</a>'+
	'		</div>'+
	'	</div>'+
	'	<div id="btnEstadia" class="navdropdown">'+
	'		<span class="nes-text is-primary navspan">Estadias</span>'+
	'		<div class="hidden">'+
	'			<a class="nes-text is-primary navlink" href="../TipoEstadia/TipoEstadia.html">Tipos de estadia</a>'+
	'			<a class="nes-text is-primary navlink" href="../Estadia/Estadia.html">Estadias</a>'+
	'		</div>'+
	'	</div>'+
	'</nav>';
});

