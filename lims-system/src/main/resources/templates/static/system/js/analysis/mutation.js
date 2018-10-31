

function showLeve(mongoId) {
	$('#mongoId').val(mongoId);
	$.ajax({
		type : "GET",
		url : PATH + "/testing/getEvidenceByMongoId.do",
		data : {
			mongoId : mongoId
		},
		dataType : "json",
		success : function(data) {
			console.info(data)
			$('.evidenceValue').val(0);
			$('.desc').val("")
			for ( var k in data) {
				$.each($('.evidence'), function(index, obj) {
					if (k == $(obj).html()) {
						$(obj).next().next().find('input').val(data[k].score);
						$(obj).next().find('input').val(data[k].note)

					}
				})

			}

		}

	})
	$('#level').modal({
		width : '420px'
	})
}
function showExampleModalLong(id, pic, mongoId) {

	// 请求mongo数据
	$.ajax({
				type : "GET",
				url : PATH + "/testing/getMutationDetail.do",
				data : {
					objectId : id
				},
				dataType : "json",
				success : function(data) {

					$('#collect2').attr('src', $('.'+id).attr('src'));
					$('#collect2').attr('onclick',
							"mutationCollection('" + mongoId + "',this)");

					var json = data;
					$('[id^=tag]').empty();
					var InterACMG = "";
					for ( var k in json.Mygeno_InterACMG) {
						if (k != "name") {
							$('#tag51').append(
									'<tr><td class="table-active">' + k
											+ '</td><td>'
											+ json.Mygeno_InterACMG[k].note
											+ '</td></tr>');

							InterACMG = InterACMG + "&nbsp" + k
						}

					}
					$('#tag2').html(InterACMG);
					if (typeof (json.遗传来源) != 'undefined') {

						$('#tag10').html(json.遗传来源.之父基因型);

						$('#tag11').html(json.遗传来源.之父测序深度);

						$('#tag12').html(json.遗传来源.之母基因型);

						$('#tag13').html(json.遗传来源.之母测序深度);
						$('#tag33').html(json.遗传来源.name);
					}

					$('#tag1').html(json.Pathogenic_Analysis);

					$('#tag3').html(json.Gene_Symbol);
					$('#tag4').html(json.ID);
					if (typeof (json.AA_change) != 'undefined') {
						$('#tag5').html(
								json.AA_change.Ref_Transcript + ":"
										+ json.AA_change.Exon + ":"
										+ json.AA_change.Nucleotide_Changes
										+ ":"
										+ json.AA_change.Amino_Acid_Changes);
					}

					$('#tag6').html(json.Gene_Type);
					$('#tag7').html(json.Depth);
					$('#tag8').html(json.MutRatio);
					$('#tag9').html(json.dbsnp);

					if (typeof (json.Total_Score) != "undefined") {
						$('#tag14').html(json.Total_Score.Phenolyzer_Score);
						$('#tag15').html(
								json.Total_Score.EXOMISER_GENE_COMBINED_SCORE);
						$('#tag16').html(json.Total_Score.Total_Score);
					}

					if (typeof (json.DiseaseTotal) != "undefined") {
						var diseaseAndInhert = "";
						if (typeof (json.DiseaseTotal.Disease) != "undefined") {
							diseaseAndInhert = diseaseAndInhert+json.DiseaseTotal.Disease+";";
						}
						if (typeof (json.DiseaseTotal.Inhert) != "undefined") {
							diseaseAndInhert = diseaseAndInhert+json.DiseaseTotal.Inhert;
						}
						$('#tag17').html(diseaseAndInhert);
						$('#tag18').html(json.DiseaseTotal.DiseaseInformation);
						$('#tag19').html(json.DiseaseTotal.DiseasePhenotype);
					}
					if (typeof (json['Highest-MAF']) != "undefined") {
						$('#tag20').html(json['Highest-MAF'].name);
						$('#tag21').html(json['Highest-MAF'].Inhouse);
						$('#tag22').html(json['Highest-MAF'].ESP6500si);
						$('#tag23').html(
								json['Highest-MAF']['1000g2015aug_all']);
						$('#tag24').html(json['Highest-MAF']['ExAC_ALL']);
						$('#tag25').html(json['Highest-MAF']['ExAC_EAS']);
						$('#tag26').html(
								json['Highest-MAF']['gnomAD_exome_ALL']);
						$('#tag52').html(
								json['Highest-MAF']['gnomAD_exome_exome_EAS']);
						$('#tag53').html(
								json['Highest-MAF']['gnomAD_genome_ALL']);
						$('#tag54').html(
								json['Highest-MAF']['gnomAD_exome_genome_EAS']);
					}

					$('#tag34').html(json.clinvar);
					$('#tag35').html(json.InterVar);
					$('#tag36').html(json.MutInDatabase);
					if (typeof (json.REVEL_score) != "undefined") {
						if (typeof (json.REVEL_score.SIFT_Predict) != "undefined"
								&& typeof (json.REVEL_score.SIFT) != "undefined") {
							$('#tag27').html(
									json.REVEL_score.SIFT_Predict + "("
											+ json.REVEL_score.SIFT + ")");

						}

						if (typeof (json.REVEL_score.PolyPhen_2_Predict) != "undefined"
								&& typeof (json.REVEL_score.PolyPhen_2) != "undefined") {
							$('#tag28')
									.html(
											json.REVEL_score.PolyPhen_2_Predict
													+ "("
													+ json.REVEL_score.PolyPhen_2
													+ ")");
						}

						if (typeof (json.REVEL_score.MutationTaster_Predict) != "undefined"
								&& typeof (json.REVEL_score.MutationTaster) != "undefined") {
							$('#tag29').html(
									json.REVEL_score.MutationTaster_Predict
											+ "("
											+ json.REVEL_score.MutationTaster
											+ ")");
						}

						if (typeof (json.REVEL_score['GERP++']) != "undefined"
							&& typeof (json.REVEL_score['GERP++_Predict']) != "undefined") {
							 $('#tag30').html(json.REVEL_score['GERP++_Predict']+"("+json.REVEL_score['GERP++']+")");
						}
						if (typeof (json.REVEL_score.MCAP_pred) != "undefined"
								&& typeof (json.REVEL_score.MCAP_score) != "undefined") {
							$('#tag31')
									.html(
											json.REVEL_score.MCAP_pred
													+ "("
													+ json.REVEL_score.MCAP_score
													+ ")");

						}

						$('#tag32').html(json.REVEL_score.SPIDEX);
					}

					$('#tag50').html(json.DiseasePhenotype);
					if (typeof (json.Tag) != "undefined") {
						$('#tag55').html(json.Tag.name);
						$('#tag56').html(json.Tag.hgvs);
						$('#tag57').html(json.Tag.report_disease);
						$('#tag58').html("<a href='http://www.ncbi.nlm.nih.gov/pubmed/?term="+json.Tag.Publication+"' target='_blanck'>"+json.Tag.Publication+"</a>");
						
					}

				}
			});

	$('#exampleModalLong').modal({
		width : '1020px'
	})
}



function subEvidence() {
	var flag = true;
	var key = [];
	var val = [];
	var desc = [];
	$.each($('.evidence'), function(index, obj) {

		if ($(obj).next().next().find('input').val() != ''
				&& $(obj).next().next().find('input').val() > 0) {
			key.push($(obj).html());
		}

	});

	$.each($('.evidenceValue'), function(index, obj) {
		if (isNaN($(obj).val())) {
			alert("请输入有效的数字");
			flag = false;
		} else {
			if ($(obj).val() < 0 || $(obj).val() > 10) {
				alert("请输入有效的数字");
				flag = false;
			}
			if ($(obj).val() != '' && $(obj).val() > 0) {
				val.push($(obj).val());
			}
		}

	});

	$.each($('.desc'), function(index, obj) {
		if ('' == $(obj).find('input').val()
				&& 0 < $(obj).next().find('input').val()) {
			alert("请输入描述！");
			flag = false;
		}

		if ($(obj).next().find('input').val() != ''
				&& $(obj).next().find('input').val() > 0) {
			desc.push($(obj).find('input').val());
		}
	});

	if (!flag) {
		return;
	}

	$.ajax({
		type : "GET",
		url : PATH + "/testing/uploadEvidence.do",
		contentType : "application/json",
		data : {
			evidence : key.join(","),
			evidenceValue : val.join(","),
			desc : desc.join(","),
			mongoId : $('#mongoId').val()
		},
		dataType : "json",
		success : function(data) {
			if ('0000' == data.statusCode) {
				alert("提交成功！");
				$('#level').modal('hide');
                loadindex = parent.layer.load();
				document.location.reload();
			}

		}
	});
}