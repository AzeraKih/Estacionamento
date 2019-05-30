var status = "wait"; //indica se é inserção/edição/espera
var selected = "null";

atualizaTable();
$("DS_MARCA").val("");

//Botão salvar, insere ou atualiza de acordo com o valor da variavel status
$("#btnSave").on("click", function(){
	
	//Se o status for aguardando, pede pro usuario inserir ou editar
	if(status == "wait"){
		alert("Clique em NOVO para inserir um novo registro ou em editar para editar um registro!");
		return;
	} else if(status == "inserir"){
		//Caso seja inserir
		let CD_MARCA = $("#CD_MARCA").html();
		let DS_MARCA = $("#DS_MARCA").val();
		let StrErro = "";
		//Valida codigo da marca
		if(CD_MARCA != "-"){
			alert("Erro, Recarregue a pagina!");
			return;
		}
		//Validação dos demais campos
		if(DS_MARCA == ""){
			$("#DS_MARCA").addClass("is-error");
			StrErro = "\nNome da marca não pode ser vazio!"
		}
		//Exibe se tiver erros
		if (StrErro != ""){
			alert("ERRO!" + StrErro);
			return;
		}
		
		//faz a requisição de inserção para o servlet
		$.ajax({
			type: "POST",
			url: "../../SrvMarca?type=insert",
			dataType:'json',
			data: {
				//Insere codigo 0 pois no banco a trigger faz o update para o codigo certo
				"OBJ":JSON.stringify({
					"CD_MARCA": 0,  
					"DS_MARCA": DS_MARCA
				})
			},
			success: function(response){
				alert(response == 1?"Inserido com sucesso!":"Erro ao inserir!");
				atualizaTable();
			},
			error:function(error){
				alert("Erro ao inserir!");
				console.log("error", error);
			},
			
		});
	//Se o status for editando	
	}else if(status === "editar"){
		
		//Carrega os campos
		let CD_MARCA = $("#CD_MARCA").html();
		let DS_MARCA = $("#DS_MARCA").val();
		let StrErro = "";
		
		//Valida o campo codigo
		if(CD_MARCA == "-"){
			alert("Erro, Recarregue a pagina!");
			return;
		}
		//Valida os demais campos
		if(DS_MARCA == ""){
			$("#DS_MARCA").addClass("is-error");
			StrErro = "\nNome da marca não pode ser vazio!"
		}
		if (StrErro != ""){
			alert("ERRO!" + StrErro);
			return;
		}
		
		//faz a requisição de update para o servlet
		$.ajax({
			type: "POST",
			url: "../../SrvMarca?type=update",
			dataType:'json',
			data: {
				"OBJ":JSON.stringify({
					"CD_MARCA": CD_MARCA,
					"DS_MARCA": DS_MARCA
				})
			},
			success: function(re){
				alert(re == 1?"Alterado com sucesso!":"Erro ao alterar!");
				atualizaTable();
			},
			error:function(error){
				alert("Erro ao alterar!");
				console.log("error",error);
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
	$("#CD_MARCA").html("-");
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
        url: "../../SrvMarca?type=select",
        dataType:'json',
        data: {
        	"WHERE": ""
        },
        success: function(re){
        	
        	$("#divTable").html("");
            var divHeader = $("<div class='tableHeader'>");
            
            divHeader.append(($("<div class='column c25'>").append($("<p>Codigo</p>"))));
            divHeader.append(($("<div class='column c75'>").append($("<p>Nome</p>"))));
            
            $("#divTable").append(divHeader);
            var divBody = $("<div class='tableBody'>"); 
            
            for(i = 0 ; i < re.Obj.length; i++){
            	let divRow = $("<div class='row rbody r" + i + "'>");
        		divRow.append(($("<div class='column c25 CD_MARCA'>").append($("<p>" + re.Obj[i].CD_MARCA + "</p>"))));
        		divRow.append(($("<div class='column c75 DS_MARCA'>").append($("<p>" + re.Obj[i].DS_MARCA + "</p>"))));
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
	
	let CD_MARCA = $($($(".row.rbody." + selected).find(".CD_MARCA").html())[0]).html();
	let DS_MARCA = $($($(".row.rbody." + selected).find(".DS_MARCA").html())[0]).html();
	
	if (CD_MARCA == ""){
		return;
	}
	console.log(CD_MARCA);
	if (confirm("Tem certeza que deseja excluir a marca " + DS_MARCA + "?")) {
		$.ajax({
			type: "POST",
			url: "../../SrvMarca?type=delete",
			dataType:'json',
			data: {
				"CD_MARCA": CD_MARCA
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
	
	let CD_MARCA = $($($(".row.rbody." + selected).find(".CD_MARCA").html())[0]).html();
	let DS_MARCA = $($($(".row.rbody." + selected).find(".DS_MARCA").html())[0]).html();
	
	$("#mainForm").find("input[type='text']").removeAttr("disabled"); //habilida campos
	$("#CD_MARCA").html(CD_MARCA);
	$("#DS_MARCA").val(DS_MARCA);
	
	//Valida o Codigo da marca
	if (CD_MARCA == ""){
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






















