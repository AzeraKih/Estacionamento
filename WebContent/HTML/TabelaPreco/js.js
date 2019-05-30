var status = "wait"; //indica se é inserção/edição/espera
var selected = "null";

atualizaTable();
$("DS_TIPO_VEICULO").val("");

//Botão salvar, insere ou atualiza de acordo com o valor da variavel status
$("#btnSave").on("click", function(){
	
	let CD_TABELA_TIPO = $("#CD_TABELA_TIPO").html();
	let DS_TIPO_VEICULO = $("#DS_TIPO_VEICULO").val();
	let PR_P15  = $("#PR_P15").val().replace(",", ".");
	let PR_P30  = $("#PR_P30").val().replace(",", ".");
	let PR_P60  = $("#PR_P60").val().replace(",", ".");
	let PR_D15  = $("#PR_D15").val().replace(",", ".");
	let PR_PDIA = $("#PR_PDIA").val().replace(",", ".");
	let PR_PMES = $("#PR_PMES").val().replace(",", ".");
	let StrErro = "";
	
	function validaCampos(){
		if(DS_TIPO_VEICULO == ""){
			$("#DS_TIPO_VEICULO").addClass("is-error");
			StrErro = "\nNome do tipo não pode ser vazio!"
		}
		if(PR_P15 == ""){
			$("PR_P15").addClass("is-error");
			StrErro = "\nCampo primeiros 15Min não pode ser vazio!"
		}
		if(PR_P30 == ""){
			$("PR_P30").addClass("is-error");
			StrErro = "\nCampo primeiros 30Min não pode ser vazio!"
		}
		if(PR_P60 == ""){
			$("PR_P60").addClass("is-error");
			StrErro = "\nCampo primeiros 60Min não pode ser vazio!"
		}
		if(PR_D15 == ""){
			$("PR_D15").addClass("is-error");
			StrErro = "\nCampo demais 15Min não pode ser vazio!"
		}
		if(PR_PDIA == ""){
			$("PR_PDIA").addClass("is-error");
			StrErro = "\nCampo preço diaria não pode ser vazio!"
		}
		if(PR_PMES == ""){
			$("PR_PMES").addClass("is-error");
			StrErro = "\nCampo preço mensal não pode ser vazio!"
		}
	}
	
	//Se o status for aguardando, pede pro usuario inserir ou editar
	if(status == "wait"){
		alert("Clique em NOVO para inserir um novo registro ou em editar para editar um registro!");
		return;
		
	//Caso seja inserir		
	} else if(status == "inserir"){
		
		//Valida codigo
		if(CD_TABELA_TIPO != "-"){
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
			url: "../../SrvTabelaTipo?type=insert",
			dataType:'json',
			data: {
				//Insere codigo 0 pois no banco a trigger faz o update para o codigo certo
				"OBJ":JSON.stringify({
					"CD_TABELA_TIPO": 0,  
					"DS_TIPO_VEICULO": DS_TIPO_VEICULO,
					"PR_P15": 		  PR_P15,
					"PR_P30":		  PR_P30,
					"PR_P60":		  PR_P60,
					"PR_D15":		  PR_D15,
					"PR_PDIA":		  PR_PDIA,
					"PR_PMES":		  PR_PMES
				})
			},
			success: function(response){
				alert(response == 1?"Inserido com sucesso!":"Erro ao inserir!");
				atualizaTable();
			},
			error:function(error){
				if (error.responseText.includes("is not a number")){
					alert("Favor verifique os valores dos campos, eles devem ser em reais");
				}else{
					alert("Erro ao inserir!" + error.responseText);
				}				
				console.log("error: ", error.responseText);
			}
			
		});
	//Se o status for editando	
	}else if(status === "editar"){
		
		//Carrega os campos
		let StrErro = "";
		
		//Valida o campo codigo
		if(CD_TABELA_TIPO == "-"){
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
			url: "../../SrvTabelaTipo?type=update",
			dataType:'json',
			data: {
				"OBJ":JSON.stringify({
					"CD_TABELA_TIPO": CD_TABELA_TIPO,  
					"DS_TIPO_VEICULO": DS_TIPO_VEICULO,
					"PR_P15": 		  PR_P15,
					"PR_P30":		  PR_P30,
					"PR_P60":		  PR_P60,
					"PR_D15":		  PR_D15,
					"PR_PDIA":		  PR_PDIA,
					"PR_PMES":		  PR_PMES
				})
			},
			success: function(re){
				alert(re == 1?"Alterado com sucesso!":"Erro ao alterar!");
				atualizaTable();
			},
			error:function(error){
				if (error.responseText.includes("is not a number")){
					alert("Favor verifique os valores dos campos, eles devem ser em reais");
				}else{
					alert("Erro ao editar!" + error.responseText);
				}				
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
	$("#mainForm").find("input[type='text']").removeAttr("disabled"); //habilida campos
	
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
	$("#mainForm").find("input[type='text']").val(""); //limpa os campos
	$("#CD_TABELA_TIPO").html("-");
	$("#mainForm").find("input[type='text']").attr("disabled", "disabled"); //desabilita campos
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
        url: "../../SrvTabelaTipo?type=select",
        dataType:'json',
        data: {
        	"WHERE": ""
        },
        success: function(re){
        	$("#divTable").html("");
            var divHeader = $("<div class='tableHeader'>");
            
            divHeader.append(($("<div class='column c10'>").append($("<p>Codigo</p>"))));
            divHeader.append(($("<div class='column c20'>").append($("<p>Tipo</p>"))));
            divHeader.append(($("<div class='column c10'>").append($("<p>P15 R$</p>"))));
            divHeader.append(($("<div class='column c10'>").append($("<p>P30 R$</p>"))));
            divHeader.append(($("<div class='column c10'>").append($("<p>P60 R$</p>"))));
            divHeader.append(($("<div class='column c10'>").append($("<p>D15 R$</p>"))));
            divHeader.append(($("<div class='column c15'>").append($("<p>PDia R$</p>"))));
            divHeader.append(($("<div class='column c15'>").append($("<p>DMes R$</p>"))));
            
            $("#divTable").append(divHeader);
            var divBody = $("<div class='tableBody'>"); 
            
            for(i = 0 ; i < re.Obj.length; i++){
            	let divRow = $("<div class='row rbody r" + i + "'>");
        		divRow.append(($("<div class='column c10 CD_TABELA_TIPO'>").append($("<p>" + re.Obj[i].CD_TABELA_TIPO + "</p>"))));
        		divRow.append(($("<div class='column c20 DS_TIPO_VEICULO'>").append($("<p>" + re.Obj[i].DS_TIPO_VEICULO + "</p>"))));
        		divRow.append(($("<div class='column c10 PR_P15'>").append($("<p>" + re.Obj[i].PR_P15.toFixed(2) + "</p>"))));
        		divRow.append(($("<div class='column c10 PR_P30'>").append($("<p>" + re.Obj[i].PR_P30.toFixed(2) + "</p>"))));
        		divRow.append(($("<div class='column c10 PR_P60'>").append($("<p>" + re.Obj[i].PR_P60.toFixed(2) + "</p>"))));
        		divRow.append(($("<div class='column c10 PR_D15'>").append($("<p>" + re.Obj[i].PR_D15.toFixed(2) + "</p>"))));
        		divRow.append(($("<div class='column c15 PR_PDIA'>").append($("<p>" + re.Obj[i].PR_PDIA.toFixed(2) + "</p>"))));
        		divRow.append(($("<div class='column c15 PR_PMES'>").append($("<p>" + re.Obj[i].PR_PMES.toFixed(2) + "</p>"))));

        		$(divRow).attr("onclick", "rowClick('r" + i + "')");
            	divBody.append(divRow);
            }
            
            $("#divTable").append(divBody);
            
        },
        error:function(error){
            console.log("error",error);
        },
    });
}

$("#btnExcluir").on("click", function(){
	
	let CD_TABELA_TIPO = $($($(".row.rbody." + selected).find(".CD_TABELA_TIPO").html())[0]).html();
	let DS_TIPO_VEICULO = $($($(".row.rbody." + selected).find(".DS_TIPO_VEICULO").html())[0]).html();
	
	if (CD_TABELA_TIPO == ""){
		return;
	}
	if (confirm("Tem certeza que deseja excluir o tipo " + DS_TIPO_VEICULO + "?")) {
		$.ajax({
			type: "POST",
			url: "../../SrvTabelaTipo?type=delete",
			dataType:'json',
			data: {
				"CD_TABELA_TIPO": CD_TABELA_TIPO
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
	
	let CD_TABELA_TIPO = $($($(".row.rbody." + selected).find(".CD_TABELA_TIPO").html())[0]).html();
	let DS_TIPO_VEICULO = $($($(".row.rbody." + selected).find(".DS_TIPO_VEICULO").html())[0]).html();
	let PR_P15 = $($($(".row.rbody." + selected).find(".PR_P15").html())[0]).html();  
	let PR_P30 = $($($(".row.rbody." + selected).find(".PR_P30").html())[0]).html();  
	let PR_P60 = $($($(".row.rbody." + selected).find(".PR_P60").html())[0]).html();  
	let PR_D15 = $($($(".row.rbody." + selected).find(".PR_D15").html())[0]).html();  
	let PR_PDIA = $($($(".row.rbody." + selected).find(".PR_PDIA").html())[0]).html();  
	let PR_PMES = $($($(".row.rbody." + selected).find(".PR_PMES").html())[0]).html();  
	
	$("#mainForm").find("input[type='text']").removeAttr("disabled"); //habilida campos
	$("#CD_TABELA_TIPO").html(CD_TABELA_TIPO);
	$("#DS_TIPO_VEICULO").val(DS_TIPO_VEICULO);
	$("#PR_P15").val(PR_P15);  
	$("#PR_P30").val(PR_P30);  
	$("#PR_P60").val(PR_P60);  
	$("#PR_D15").val(PR_D15);  
	$("#PR_PDIA").val(PR_PDIA);  
	$("#PR_PMES").val(PR_PMES);  
	
	//Valida o Codigo
	if (CD_TABELA_TIPO == ""){
		return;
	}
	
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
	$("#mainForm").find("input[type='text']").val("");
});

$('#btnReload').on("click", function(){
	atualizaTable();
});





















