$("#btnVeiculo").on("click", function(){
	$("#btnVeiculo>div").toggleClass("hidden");
});

$("#btnEstadia").on("click", function(){
	$("#btnEstadia>div").toggleClass("hidden");
});

$("#btnEstadia").on("mouseleave", function(){
	$("#btnEstadia>div").addClass("hidden");
});

$("#btnVeiculo").on("mouseleave", function(){
	$("#btnVeiculo>div").addClass("hidden");
});

function rowClick(rowId){	
	selected = rowId;
	if(rowId != ""){
		if(status == "wait"){
			$(".row.rbody").removeClass("selected");
			$("." + rowId).addClass("selected");
			$("#btnEditar").removeClass("is-disabled");
			$("#btnEditar").addClass("is-warning");
			$("#btnExcluir").removeClass("is-disabled");
			$("#btnExcluir").addClass("is-error");
		}
	}else{
		$(".row.rbody").removeClass("selected");
		$("#btnEditar").removeClass("is-warning");
		$("#btnEditar").addClass("is-disabled");
		$("#btnExcluir").removeClass("is-error");
		$("#btnExcluir").addClass("is-disabled");
	}
}