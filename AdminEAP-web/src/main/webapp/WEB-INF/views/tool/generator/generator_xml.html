<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        <li class="fa fa-remove"></li>
    </button>
    <h5 class="modal-title"></h5>
</div>

<div class="modal-body">
    <div class="box-body">
        <div class="col-md-12">
            <textarea cols="30" rows="10" name="xmlContent" id="xmlContent"
                      style="width: 100%;height:400px;font-size:14px; color:rgb(63,127,127);font-family:微软雅黑;"></textarea>
        </div>
    </div>
    <!-- /.box-body -->
    <div class="box-footer text-center">
        <span>将xml配置添加到文件中，文件名：</span>
        <input type="text" name="xmlFile" id="xmlfile">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" data-btn-type="generateXML" onclick="startGenerate()">生成xml配置</button>
    </div>
</div>
<script>
    var queryId="${queryId}";
    var className="${className}";
    var modelName="${modelName}";
    $(function(){
        var arr=className.split("\.");
        var xmlfile="";
        if(arr.length>4){
            xmlfile=arr[arr.length-3];
            $("#xmlfile").val(xmlfile+".xml");
        }
        ajaxPost(basePath+"/generator/getXMLContent",{queryId:queryId,className:className,modelName:modelName},function(data){
            if(data.success){
                $("#xmlContent").val(data.message);
            }
        });
    })

    //生成xml配置
    function startGenerate(){
        var xmlContent=$("#xmlContent").val();
        var xmlFile=$("#xmlfile").val();
        if(!xmlFile){
            modals.info("请选择xml要保存的文件名");
            return;
        }
        ajaxPost(basePath+"/generator/generateXMLFile",{xmlContent:xmlContent,xmlFile:xmlFile},function(data){
           if(data.success){
               modals.correct(data.message);
           }
        });
    }

</script>
