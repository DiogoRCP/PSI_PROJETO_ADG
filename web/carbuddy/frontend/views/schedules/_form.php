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

    <?= $form->field($model, 'schedulingdate')->input('datetime-local') ?>

    <?= $form->field($model, 'repairdescription')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'state')->textInput(['readonly' => true, 'value'=>"Pending"]) ?>

    <?= $form->field($model, 'repairtype')->dropDownList(['' => '','Repair' => 'Repair', 'Maintenance' => 'Maintenance']) ?>

    <?= $form->field($model, 'carId')->dropDownList($CarList) ?>

    <?= $form->field($model, 'companyId')->dropDownList($CompaniesList) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
