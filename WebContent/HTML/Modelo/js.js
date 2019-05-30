var status = "wait"; //indica se é inserção/edição/espera
var selected = "null";

atualizaTable();
atualizaSelects();
desabilitaCampos();


//Botão salvar, insere ou atualiza de acordo com o valor da variavel status
$("#btnSave").on("click", function(){
	
	let CD_MODELO = $("#CD_MODELO").html();
	let DS_MODELO = $("#DS_MODELO").val();
	let DT_ANO = $("#DT_ANO").val();
	let CD_MARCA  = $("#CD_MARCA").val();
	let CD_TABELA_TIPO  = $("#CD_TABELA_TIPO").val();
	let StrErro = "";
	
	function validaCampos(){
		if(DS_MODELO == ""){
			$("#DS_TIPO_VEICULO").addClass("is-error");
			StrErro = "\nNome do tipo não pode ser vazio!";
		}
		if(CD_MARCA == ""){
			$("CD_MARCA").addClass("is-error");
			StrErro = "\nMarca nao pode ser vazio";
		}
		if(CD_TABELA_TIPO == ""){
			$("CD_TABELA_TIPO").addClass("is-error");
			StrErro = "\nTipo não pode ser vazio";
		}
		if(DT_ANO == ""){
			$("DT_ANO").addClass("is-error");
			StrErro = "\nAno não pode ser vazio";
		}
	}
	

	//Se o status for aguardando, pede pro usuario inserir ou editar
	if(status == "wait"){
		alert("Clique em NOVO para inserir um novo registro ou em editar para editar um registro!");
		return;
		
	//Caso seja inserir		
	} else if(status == "inserir"){
		
		//Valida codigo
		if(CD_MODELO != "-"){
			alert("Erro, Recarregue a pagina!");
			return;
		}
		
		//Validação dos demais campos
		validaCampos();
		//Exibe se tiver erros
		if (StrErro != ""){
			alert("ERRO!" + StrErro);
			return;
		}
		//faz a requisição de inserção para o servlet
		$.ajax({
			type: "POST",
			url: "../../SrvModelo?type=insert",
			dataType:'json',
			data: {
				//Insere codigo 0 pois no banco a trigger faz o update para o codigo certo
				"OBJ":JSON.stringify({
					"CD_MODELO": 0,  
					"DS_MODELO": DS_MODELO,
					"DT_ANO": DT_ANO,
					"CD_MARCA" : CD_MARCA,
					"CD_TABELA_TIPO": CD_TABELA_TIPO
				})
			},
			success: function(response){
				alert(response == 1?"Inserido com sucesso!":"Erro ao inserir!");
				atualizaTable();
			},
			error:function(error){
				alert("Erro ao inserir!" + error.responseText);				
				console.log("error: ", error.responseText);
			}
			
		});
	//Se o status for editando	
	}else if(status === "editar"){
				
		//Valida o campo codigo
		if(CD_MODELO == "-"){
			alert("Erro, Recarregue a pagina!");
			return;
		}
		
		//Valida os demais campos
		validaCampos();
		
		if (StrErro != ""){
			alert("ERRO!" + StrErro);
			return;
		}
		
		//faz a requisição de update para o servlet
		$.ajax({
			type: "POST",
			url: "../../SrvModelo?type=update",
			dataType:'json',
			data: {
				"OBJ":JSON.stringify({
					"CD_MODELO": CD_MODELO,  
					"DS_MODELO": DS_MODELO,
					"DT_ANO": DT_ANO,
					"CD_MARCA" : CD_MARCA,
					"CD_TABELA_TIPO": CD_TABELA_TIPO
				})
			},
			success: function(re){
				alert(re == 1?"Alterado com sucesso!":"Erro ao alterar!");
				atualizaTable();
			},
			error:function(error){
				alert("Erro ao editar!" + error.responseText);				
				console.log("error: ", error.responseText);
			}			
		});
	}else{		
		alert("Erro, recarregue a pagina!");		
	}
	
	$("#btnCancel").click();
	//Dispara ação click do cancel para resetar o status da pagina
});


$("#btnNovo").on("click", function(){

	habilitaCampos();
	
	$("#btnCancel").addClass("is-error");
	$("#btnCancel").removeClass("is-disabled");
	$("#btnClear").addClass("is-warning");
	$("#btnClear").removeClass("is-disabled");
	$("#btnSave").addClass("is-success");
	$("#btnSave").removeClass("is-disabled");
	
	//habilita botoes
	$("#btnNovo").addClass("is-disabled");
	$("#btnNovo").removeClass("is-success");
	$("#btnSave").addClass("is-success");
	$("#btnSave").removeClass("is-disabled");
	
	rowClick("");
	
	status = "inserir"; //seta status inserir	
});

$("#btnCancel").on("click", function(){

	desabilitaCampos();
	
	//Habilida botoes
	$("#btnCancel").addClass("is-disabled");
	$("#btnCancel").removeClass("is-error");
	$("#btnClear").addClass("is-disabled");
	$("#btnClear").removeClass("is-warning");
	$("#btnSave").addClass("is-disabled");
	$("#btnSave").removeClass("is-success");
	
	//Desabilita botoes
	$("#btnNovo").addClass("is-success");
	$("#btnNovo").removeClass("is-disabled");
	
	status = "wait"
	rowClick("");
	
});


function atualizaTable(){
	$("#btnCancel").click();
	$("#divTable").html("<h1>Carregando ...</h1><h3>Aguarde</h3>");
    $.ajax({
        type: "POST",
        url: "../../SrvModelo?type=select",
        dataType:'json',
        data: {
        	"WHERE": ""
        },
        success: function(re){
        	$("#divTable").html("");
            var divHeader = $("<div class='tableHeader'>");
            
            divHeader.append(($("<div class='column c25'>").append($("<p>Codigo</p>"))));
            divHeader.append(($("<div class='column c25'>").append($("<p>Nome</p>"))));
            divHeader.append(($("<div class='column c10'>").append($("<p>Ano</p>"))));            
            divHeader.append(($("<div class='column c15'>").append($("<p>Marca</p>"))));
            divHeader.append(($("<div class='column c25'>").append($("<p>Tipo</p>"))));
            
            $("#divTable").append(divHeader);
            var divBody = $("<div class='tableBody'>"); 
            if(re.Obj){
	            for(i = 0 ; i < re.Obj.length; i++){
	            	let divRow = $("<div class='row rbody r" + i + "'>");
	        		divRow.append($("<div class='column c25 CD_MODELO'>").append($("<p>" + re.Obj[i].CD_MODELO + "</p>")));
	        		divRow.append($("<div class='column c25 DS_TIPO_VEICULO'>").append($("<p>" + re.Obj[i].DS_MODELO + "</p>")));
	        		divRow.append($("<div class='column c10 DT_ANO'>").append($("<p>" + re.Obj[i].DT_ANO + "</p>")));
	        		divRow.append($("<div class='column c15 CD_MARCA' value='" + re.Obj[i].CD_MARCA + "'>").append($("<p>" + re.Obj[i].DS_MARCA + "</p>")));
	        		divRow.append($("<div class='column c25 CD_TABELA_TIPO' value='" + re.Obj[i].CD_TABELA_TIPO + "'>").append($("<p>" + re.Obj[i].DS_TIPO_VEICULO + "</p>")));
	        		
	        		$(divRow).attr("onclick", "rowClick('r" + i + "')");
	            	divBody.append(divRow);
	            }
            }
            
            $("#divTable").append(divBody);
            
        },
        error:function(error){
            console.log("error",error);
        },
    });
}

function atualizaSelects(){
	$.ajax({
        type: "POST",
        url: "../../SrvMarca?type=select",
        dataType:'json',
        data: {
        	"WHERE": ""
        },
        success: function(re){
        	$("#CD_MARCA").html("<option value='' disabled selected hidden>Selecione</option>");
        	let sMarca = $("#CD_MARCA");
            
            for(i = 0 ; i < re.Obj.length; i++){
            	let sOpt = $("<option value=" + re.Obj[i].CD_MARCA + ">");
            	sOpt.html(re.Obj[i].DS_MARCA);
            	sMarca.append(sOpt);
            }
        },
        error:function(error){
            console.log("error",error);
        },
    });
	
	$.ajax({
        type: "POST",
        url: "../../SrvTabelaTipo?type=select",
        dataType:'json',
        data: {
        	"WHERE": ""
        },
        success: function(re){
        	$("#CD_TABELA_TIPO").html("<option value='' disabled selected hidden>Selecione</option>");
        	let sTipo = $("#CD_TABELA_TIPO");
            for(i = 0 ; i < re.Obj.length; i++){
            	let sOpt = $("<option value=" + re.Obj[i].CD_TABELA_TIPO + ">");
            	sOpt.html(re.Obj[i].DS_TIPO_VEICULO);
            	sTipo.append(sOpt);
            }
        },
        error:function(error){
            console.log("error",error);
        },
    });
}

$("#btnExcluir").on("click", function(){
	
	let CD_MODELO = $($($(".row.rbody." + selected).find(".CD_MODELO").html())[0]).html();
	let DS_MODELO = $($($(".row.rbody." + selected).find(".DS_MODELO").html())[0]).html();
	
	if (CD_MODELO == ""){
		return;
	}
	
	if (confirm("Tem certeza que deseja excluir o tipo " + DS_MODELO + "?")) {
		$.ajax({
			type: "POST",
			url: "../../SrvModelo?type=delete",
			dataType:'json',
			data: {
				"CD_MODELO": CD_MODELO
			},
			success: function(re){
				alert(re == 1?"Excluido com sucesso!":"Erro ao excluir!");
			    atualizaTable();
			},
			error:function(error){
				alert("Erro ao excluir!");
				console.log("error",error);
			}			
		});
	}
});

$("#btnEditar").on("click", function(){
	let CD_MODELO = $($($(".row.rbody." + selected).find(".CD_MODELO").html())[0]).html();
	let DS_TIPO_VEICULO = $($($(".row.rbody." + selected).find(".DS_TIPO_VEICULO").html())[0]).html();
	let DT_ANO = $($($(".row.rbody." + selected).find(".DT_ANO").html())[0]).html();
	let CD_MARCA = $($($(".row.rbody." + selected).find(".CD_MARCA").html())[0]).attr("value");  
	let CD_TABELA_TIPO = $($($(".row.rbody." + selected).find(".CD_TABELA_TIPO").html())[0]).attr("value"); 
	console.log(CD_MARCA);
	//Valida o Codigo
	if (CD_MODELO == ""){
		return;
	}
	
	habilitaCampos();
	
	$("#CD_MODELO").html(CD_MODELO);
	$("#DS_MODELO").val(DS_TIPO_VEICULO);
	$("#DT_ANO").val(DT_ANO);  
	$("#CD_MARCA").val(CD_MARCA);  
	$("#CD_TABELA_TIPO").val(CD_TABELA_TIPO);  
	
	
	$("#btnCancel").addClass("is-error");
	$("#btnCancel").removeClass("is-disabled");
	$("#btnClear").addClass("is-warning");
	$("#btnClear").removeClass("is-disabled");
	$("#btnSave").addClass("is-success");
	$("#btnSave").removeClass("is-disabled");
 
	//desabilita botoes
	$("#btnNovo").addClass("is-disabled");
	$("#btnNovo").removeClass("is-success");
	$("#btnSave").addClass("is-success");
	$("#btnSave").removeClass("is-disabled");
	$("#btnEditar").addClass("is-disabled");
	$("#btnEditar").removeClass("is-warning");
	$("#btnExcluir").addClass("is-disabled");
	$("#btnExcluir").removeClass("is-error");
	
	status = "editar";

});


$("#btnClear").on("click", function(){
	limpaCampos(); 
});

$('#btnReload').on("click", function(){
	atualizaTable();
});

function limpaCampos(){
	$("#mainForm").find("input[type='text']").val("");
	$("#mainForm").find("input[type='number']").val("");
	$("#mainForm").find("select").val("");
}
function habilitaCampos(){
	$("#mainForm").find("input[type='text']").removeAttr("disabled");
	$("#mainForm").find("input[type='number']").removeAttr("disabled");
	$("#mainForm").find("select").removeAttr("disabled");
}
function desabilitaCampos(){
	limpaCampos();
	$("#CD_MODELO").html("-");
	$("#mainForm").find("input[type='text']").attr("disabled", "disabled"); //desabilita campos
	$("#mainForm").find("input[type='number']").attr("disabled", "disabled");
	$("#mainForm").find("select").attr("disabled", "disabled"); //desabilita campos
}










