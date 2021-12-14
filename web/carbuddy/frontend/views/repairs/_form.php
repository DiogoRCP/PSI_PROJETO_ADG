<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\Repairs */
/* @var $modelCars frontend\models\Cars */
/* @var $modelUsers backend\models\Users */
/* @var $modelContributor frontend\models\Contributors */
/* @var $form yii\widgets\ActiveForm */

// Carregar Cars para a dropDownList
$CarList = ['' => ''];
foreach ($modelCars as $modelCar) {
    $CarList += [$modelCar['id'] => $modelCar['registration']];
}
?>

<div class="repairs-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'carId')->dropDownList($CarList) ?>

    <?= $form->field($model, 'repairtype')->dropDownList(['' => '','Repair' => 'Repair', 'Maintenance' => 'Maintenance'])  ?>

    <?= $form->field($model, 'state')->dropDownList(['' => '','Under Repair' => 'Under Repair', 'Repaired' => 'Repaired', 'To Repair' => 'To Repair', 'Pending' => 'Pending'])  ?>

    <?= $form->field($model, 'kilometers')->input('number') ?>

    <?= $form->field($model, 'repairdescription')->textInput(['maxlength' => true]) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>
    </div>

</div>
