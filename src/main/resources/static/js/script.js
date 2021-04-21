function switchJobType()
{
    if (document.getElementById('cronJob').checked)
    {
        document.getElementById('endDate').style.display = "none";
        document.getElementById('repeatInterval').style.display ="none";
        document.getElementById('runForever').style.display ="none";
        document.getElementById('triggerCount').style.display ="none";
        document.getElementById('cronExpression').style.display = "inline";
    }
    else{
        document.getElementById('endDate').style.display = "inline";
        document.getElementById('repeatInterval').style.display ="inline";
        document.getElementById('runForever').style.display ="inline";
        document.getElementById('triggerCount').style.display ="inline";
        document.getElementById('cronExpression').style.display = "none";
    }

}