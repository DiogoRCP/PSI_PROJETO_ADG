<?php

/* @var $this yii\web\View */
/* @var $form yii\bootstrap4\ActiveForm */
/* @var $model \frontend\models\SignupForm */

use yii\bootstrap4\Html;
use yii\bootstrap4\ActiveForm;

$this->title = 'Signup';
?>
<div class="site-signup">
    <div class="row">
        <div class="col-lg-9">
            <img src="../images/logo_white.png" width="80">
            <h2>Sign Up</h2>
            <?php $form = ActiveForm::begin(['id' => 'form-signup']); ?>

                <?= $form->field($model, 'username')->textInput(['autofocus' => true]) ?>

                <?= $form->field($model, 'email')->label('E-mail')?>

                <?= $form->field($model, 'nif')->label('NIF')->input("number", ['max' => '999999999', 'maxlength' =>'9', 'pattern'=> '[0-9]{9}'])?>

                <?= $form->field($model, 'birsthday')->input("date") ?>

                <?= $form->field($model, 'phonenumber')->label('Phone Number')->input("tel", ['pattern'=> '[0-9]{9}']) ?>

                <?= $form->field($model, 'password')->label('Password')->passwordInput() ?>

                <div class="form-group">
                    <?= Html::submitButton('Signup', ['class' => 'btn btn-secondary', 'name' => 'signup-button']) ?>
                </div>

            <?php ActiveForm::end(); ?>
        </div>
    </div>
</div>
