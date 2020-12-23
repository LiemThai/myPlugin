function getInfoTable() {
    $.ajax({
        type: "GET",
        url: "http://localhost:2990/jira/rest/resource/1.0/users/allusers",
        dataType: 'json',
        success: function (items) {
            $("#my-table .remove-class").remove();
            $("#get-table").html(JIRA.Templates.Example.getATable({data:items}));
            var flagDelete = true;
            $(".delete").unbind().click(function (event) {
                findID(event.target.id);
                flagDelete=false;
                AJS.dialog2("#update-board").hide();
                // CONFIRM TO DELETE
                jQuery("#button-confirm").unbind().click(function () {
                    deleteUser(event.target.id);
                    AJS.flag({
                        type: 'success',
                        close:'auto',
                        body: event.target.id + ' has been deleted.'
                    });
                    AJS.dialog2("#delete-warning").hide();
                });
                // CONFIRM TO CANCEL
                jQuery("#button-cancel").unbind().click(function () {
                    AJS.dialog2("#delete-warning").hide();
                })
            });
            $(".user-board").unbind().click(function(event){
                event.preventDefault();
                if(flagDelete==true){
                    AJS.dialog2("#update-board").show();
                    var index = $(this).closest("tr").index();// GET (int) INDEX OF A CLICKED ROW
                    $("#key").val(items[index].key);
                    $("#ID").val(items[index].id);
                    $("#displayName").val(items[index].displayName);
                    $("#emailAddress").val(items[index].emailAddress);
                }
                flagDelete=true;
            })
            // CHECK DISABLE BUTTON
            setEmptyValue();
            setEnable();
        }
    })
}

function setEnable() {
    $("#ID").keyup(function() {
        var emptyUseInfo = false;
        $("#ID").each(function() {
            if ($(this).val().length === 0) {
                emptyUseInfo = true;
            }
        });
        if (!emptyUseInfo){
            $("#button-search").removeAttr('disabled');
        }
    })
}

function setEmptyValue() {
    $("#key").val("");
    $("#displayName").val("");
    $("#emailAddress").val("");
    $("#ID").val("");
    $("#button-add").attr('disabled', 'disabled');
    $("#button-search").attr('disabled', 'disabled');
    $("#button-show-all").attr('disabled', 'disabled');
}

function findID(id) {
    $.ajax({
        type: "GET",
        url: "http://localhost:2990/jira/rest/resource/1.0/users/user/" + id,
        dataType: 'json',
        success: function (data) {
            $("#user-warning").html(JIRA.Templates.Example.deleteContent({data:data}));
            AJS.dialog2("#delete-warning").show();
        }
    })
}

function findByID(){
    $.ajax({
        type: "GET",
        url: "http://localhost:2990/jira/rest/resource/1.0/users/user/" + $("#ID").val(),
        dataType: 'json',
        success: function (data) {
            $("#my-table .remove_class").remove();
            $("#get-table").html(JIRA.Templates.Example.getARow({data:data}));
            var flagDelete = true;
            $(".delete").unbind().click(function (event) {
                flagDelete=false;
                findID(event.target.id);
                AJS.dialog2("#update-board").hide();
                // CONFIRM TO DELETE
                jQuery("#button-confirm").unbind().click(function () {
                    deleteUser(event.target.id);
                    AJS.dialog2("#delete-warning").hide();
                });
                // CONFIRM TO CANCEL
                jQuery("#button-cancel").unbind().click(function () {
                    AJS.dialog2("#delete-warning").hide();
                })
            })
            $(".user-board").unbind().click(function(event){
                event.preventDefault();
                if(flagDelete==true){
                    AJS.dialog2("#update-board").show();
                    $("#key").val(data.key);
                    $("#ID").val(data.id);
                    $("#displayName").val(data.displayName);
                    $("#emailAddress").val(data.emailAddress);
                }
                flagDelete=true;
            })
            setEmptyValue();
            $("#button-show-all").removeAttr('disabled');
        }
    })
}

function deleteUser(id){
    $.ajax({
        type: "DELETE",
        url: "http://localhost:2990/jira/rest/resource/1.0/users/"+ id,
        dataType: 'json',
        success: function () {
            getInfoTable();
        }
    })
}

function addUser(){
    var item = $("#my-user-picker").auiSelect2('data');
    var user = {
        key: item.id,
        displayName: item.text,
        emailAddress: item.id+"@gmail.com",
    };
    $.ajax({
        type: 'POST',
        url: 'http://localhost:2990/jira/rest/resource/1.0/users/user',
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function () {
            AJS.flag({
                type: 'success',
                close:'auto',
                body: user.displayName + ' has been added.'
            });
            getInfoTable();
        }
    })
}

function updateUser(){
    var user = {
        key: $("#key").val(),
        displayName:$("#displayName").val(),
        emailAddress:$("#emailAddress").val()
    };
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:2990/jira/rest/resource/1.0/users/update/'+$("#ID").val(),
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function () {
            AJS.dialog2("#update-board").hide();
            getInfoTable();
        }
    })

}

require([
    'jQuery',
    'ajax',
    'aui/dialog2'
],
    jQuery(document).ready(function ($) {
        var $userPicker = $("#my-user-picker");
        $userPicker.auiSelect2({
                minimumInputLength: 1,// show chu khi co it nhat 1 ki tu
                multiple: false, // make the control a multi-select
                ajax: {
                    // JIRA-relative URL to the REST end-point
                    url: "http://localhost:2990/jira/rest/api/2/user/picker",
                    type: "GET",
                    dataType: 'json',
                    cache: true,
                    // query parameters for the remote ajax call
                    data: function data(term) {
                        return {
                            query: term,
                            maxResults: 1000,
                            showAvatar: true
                        };
                    },
                    results: function (data) {
                        return {
                            results: $.map(data.users, function (item) {
                                return {
                                    id: item.key,
                                    text: item.displayName,
                                    url: item.avatarUrl
                                }
                            })
                        }
                    }
                },
            formatResult: function (data) {
                return '<div>' +
                    '<img style="width:22px; height: 22px;   float: left; margin: 0px 10px 0px 0px;" ' +
                    'src=' + data.url + '>' + "  " + '<span">' + data.text + '</span>' +
                    '</div>'
            },
            formatSelection: function (data) {
                $("#button-add").removeAttr('disabled');
                return '<div>' +
                    '<img style="width:22px; height: 22px;   float: left; margin: 0px 10px 0px 0px;" ' +
                    'src=' + data.url + '>' + "  " + '<span>' + data.text + '</span>' +
                    '</div>'
            }
        });

        // LOAD DU LIEU LEN BANG
        getInfoTable();

        // GET A USER BY ID
        jQuery("#button-search").click(function () {
            findByID();
        }),

        // SHOW TABLE AGAIN AFTER FIND A USER
        jQuery("#button-show-all").click(function () {
            getInfoTable();
        }),

        // ADD - POST
         jQuery("#button-add").click(function (){
            addUser();
        })

        // UPDATE - PUT
        jQuery("#button-update").click(function (){
            updateUser();
        })
    })
)