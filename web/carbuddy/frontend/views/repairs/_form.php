<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\Repairs */
/* @var $modelCars frontend\models\Cars */
/* @var $modelContributor frontend\models\Contributors */
/* @var $form yii\widgets\ActiveForm */

// Carregar Users para a dropDownList
$CarList = ['' => ''];
foreach ($modelCars as $modelCars) {
    $CarList += [$modelCars['id'] => $modelCars['vin']];
}

// Carregar Companies para a dropDownList
$ContributorsList = ['' => ''];
foreach ($modelContributor as $modelContributor) {
    $ContributorsList += [$modelContributor['id'] => $modelContributor['userId']];
}
?>

<div class="repairs-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'kilometers')->textInput() ?>

    <?= $form->field($model, 'repairdescription')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'state')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'repairtype')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'carId')->dropDownList($CarList) ?>

    <?= $form->field($model, 'contributorId')->dropDownList($ContributorsList) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>
    </div>

</div>
