<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;
use frontend\models\Users;

/* @var $this yii\web\View */
/* @var $model frontend\models\Schedules */
/* @var $modelCars frontend\models\Cars */
/* @var $modelCompanies frontend\models\Companies */
/* @var $form yii\widgets\ActiveForm */

$CarList = ['' => ''];
foreach ($modelCars as $modelCar) {
    if($modelCar->user->id == \Yii::$app->user->id){
        $CarList += [$modelCar['id'] => $modelCar['registration']];
    }
}
?>

<div class="schedules-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'schedulingdate')->input('datetime-local', ['value'=>str_replace(' ','T',$model->schedulingdate)]) ?>

    <?= $form->field($model, 'repairdescription')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'repairtype')->dropDownList(['' => '','Repair' => 'Repair', 'Maintenance' => 'Maintenance']) ?>

    <?= $form->field($model, 'carId')->dropDownList($CarList) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
