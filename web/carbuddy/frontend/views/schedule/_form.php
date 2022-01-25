<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\Schedules */
/* @var $modelCars frontend\models\Cars */
/* @var $modelCompanies frontend\models\Companies */
/* @var $form yii\widgets\ActiveForm */

$CarList = ['' => ''];
foreach ($modelCars as $modelCar) {
    $CarList += [$modelCar['id'] => $modelCar['registration']];
}

$CompaniesList = ['' => ''];
foreach ($modelCompanies as $modelCompany) {
    $CompaniesList += [$modelCompany['id'] => $modelCompany['companyname']];
}
?>

<div class="schedules-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'schedulingdate')->input('datetime-local', ['value'=>str_replace(' ','T',$model->schedulingdate)]) ?>

    <?= $form->field($model, 'repairdescription')->textInput(['readonly' => true, 'maxlength' => true]) ?>

    <?= $form->field($model, 'state')->dropDownList(['' => '','Pending' => 'Pending', 'Accepted' => 'Accepted', 'Accepted with Changed Date'=>'Accepted with Changed Date']) ?>

    <?= $form->field($model, 'repairtype')->textInput(['readonly' => true, 'maxlength' => true]) ?>

    <?= $form->field($model, 'carId')->dropDownList($CarList,['disabled' => 'disabled']) ?>

    <?= $form->field($model, 'companyId')->dropDownList($CompaniesList,['disabled' => 'disabled']) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
